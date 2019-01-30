/**
 * jspice is distributed under the GNU General Public License version 3
 * and is also available under alternative licenses negotiated directly
 * with Knowm, Inc.
 *
 * Copyright (c) 2016-2017 Knowm Inc. www.knowm.org
 *
 * Knowm, Inc. holds copyright
 * and/or sufficient licenses to all components of the jspice
 * package, and therefore can grant, at its sole discretion, the ability
 * for companies, individuals, or organizations to create proprietary or
 * open source (even if not GPL) modules which may be dynamically linked at
 * runtime with the portions of jspice which fall under our
 * copyright/license umbrella, or are distributed under more flexible
 * licenses than GPL.
 *
 * The 'Knowm' name and logos are trademarks owned by Knowm, Inc.
 *
 * If you have any questions regarding our licensing policy, please
 * contact us at `contact@knowm.org`.
 */
package org.knowm.jspice.simulate.transientanalysis;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.knowm.jspice.component.Component;
import org.knowm.jspice.netlist.Netlist;
import org.knowm.jspice.netlist.spice.SPICEUtils;
import org.knowm.jspice.simulate.SimulationPlotData;
import org.knowm.jspice.simulate.SimulationPreCheck;
import org.knowm.jspice.simulate.SimulationResult;
import org.knowm.jspice.simulate.dcoperatingpoint.DCOperatingPoint;
import org.knowm.jspice.simulate.dcoperatingpoint.DCOperatingPointResult;
import org.knowm.jspice.simulate.dcoperatingpoint.NodalAnalysisConvergenceException;
import org.knowm.jspice.simulate.transientanalysis.driver.Driver;

// modified by INTechTeachTheTeensy
public class TransientAnalysis {

  private final Netlist netlist;
  private final TransientConfig transientAnalysisDefinition;
  private DCOperatingPointResult stepDC;

  /**
   * Constructor
   *
   * @param netlist
   * @param transientAnalysisDefinition
   */
  public TransientAnalysis(Netlist netlist, TransientConfig transientAnalysisDefinition) {

    this.netlist = netlist;
    this.transientAnalysisDefinition = transientAnalysisDefinition;
  }

  private BigDecimal currentTime = BigDecimal.ZERO;
  private Map<String, SimulationPlotData> timeSeriesDataMap = new LinkedHashMap<>();
  private SimulationResult simulationResult = new SimulationResult("Time [s]", "", timeSeriesDataMap);
  private DCOperatingPoint dcOperatingPoint;


  public SimulationResult stepFor(BigDecimal time, BigDecimal timeStep) {
    verify(transientAnalysisDefinition);

    BigDecimal finalTime = time.add(currentTime);

    if(stepDC == null) {
      stepDC = new DCOperatingPoint(netlist).run();
    }
    //        System.out.println(dCOperatingPointResult.toString());

    for (String nodeLabel : stepDC.getNodeLabels2Value().keySet()) {
      timeSeriesDataMap.put(nodeLabel, new SimulationPlotData());
    }
    for (String deviceID : stepDC.getDeviceLabels2Value().keySet()) {
      timeSeriesDataMap.put(deviceID, new SimulationPlotData());
    }

    for(; currentTime.compareTo(finalTime) <  0; currentTime = currentTime.add(timeStep)) {
      stepCircuit(timeSeriesDataMap, currentTime, timeStep, stepDC);
    }
    return simulationResult;
  }

  public SimulationResult run() {
    currentTime = BigDecimal.ZERO;
    timeSeriesDataMap.clear();
    // long start = System.currentTimeMillis();

    // sanity checks
    verify(transientAnalysisDefinition);

    // add single sweep result to SimulationResult
    SimulationResult simulationResult = new SimulationResult("Time [s]", "", getSingleTransientAnalyisResult());

    // System.out.println("transientAnalyis= " + (System.currentTimeMillis() - start));

    return simulationResult;
  }

  private Map<String, SimulationPlotData> getSingleTransientAnalyisResult() {

    BigDecimal firstPoint = BigDecimal.ZERO;
    BigDecimal timeStep = SPICEUtils.bigDecimalFromString(transientAnalysisDefinition.getTimeStep());
    BigDecimal stopTime = SPICEUtils.bigDecimalFromString(transientAnalysisDefinition.getStopTime());


    // get operating point to generate a node list for keeping track of time series data map
    DCOperatingPointResult dCOperatingPointResult = new DCOperatingPoint(netlist).run();
    //        System.out.println(dCOperatingPointResult.toString());

    for (String nodeLabel : dCOperatingPointResult.getNodeLabels2Value().keySet()) {
      timeSeriesDataMap.put(nodeLabel, new SimulationPlotData());
    }
    for (String deviceID : dCOperatingPointResult.getDeviceLabels2Value().keySet()) {
      timeSeriesDataMap.put(deviceID, new SimulationPlotData());
    }

    // for each time step
    for (BigDecimal t = firstPoint; t.compareTo(stopTime) < 0; t = t.add(timeStep)) {
      stepCircuit(timeSeriesDataMap, t, timeStep, dCOperatingPointResult);
    }

    // return the timeseries data
    return timeSeriesDataMap;
  }

  private void stepCircuit(Map<String, SimulationPlotData> timeSeriesDataMap, BigDecimal t, BigDecimal timeStep, DCOperatingPointResult dCOperatingPointResult) {
    // update drivers' values
    for (int i = 0; i < transientAnalysisDefinition.getDrivers().length; i++) {

      Driver driver = transientAnalysisDefinition.getDrivers()[i];
      double signal = driver.getSignal(t);
//        System.out.println(t);
//        System.out.println(signal);
//        System.out.println("---");

      Component sweepableComponent = netlist.getComponent(transientAnalysisDefinition.getDrivers()[i].getId());
      //        System.out.println("sweepableComponent " + sweepableComponent);
      sweepableComponent.setSweepValue(signal);
    }

    try {
      netlist.setInitialConditions(false);

      // solve DC operating point
      if(dcOperatingPoint == null) {
        dcOperatingPoint = new DCOperatingPoint(dCOperatingPointResult, netlist, timeStep.doubleValue());
      } else {
        dcOperatingPoint.previousDcOperatingPointResult = dCOperatingPointResult;
      }
      dCOperatingPointResult = dcOperatingPoint
              .run();
      //        System.out.println(dCOperatingPointResult.toString());

      // add all node voltage values
      for (String nodeLabel : dCOperatingPointResult.getNodeLabels2Value().keySet()) {
        if (timeSeriesDataMap.get(nodeLabel) != null) {
          timeSeriesDataMap.get(nodeLabel).getxData().add(t);
          timeSeriesDataMap.get(nodeLabel).getyData().add(dCOperatingPointResult.getNodeLabels2Value().get(nodeLabel));
        }
      }
      // add all device current values
      for (String deviceID : dCOperatingPointResult.getDeviceLabels2Value().keySet()) {
        timeSeriesDataMap.get(deviceID).getxData().add(t);
        timeSeriesDataMap.get(deviceID).getyData().add(dCOperatingPointResult.getDeviceLabels2Value().get(deviceID));
      }
    } catch (NodalAnalysisConvergenceException e) {
      System.out.println("skipping value at t= " + t + " because of failure to converge!");
    }
  }

  /**
   * sanity checks a SweepDefinition
   */
  private void verify(TransientConfig transientAnalysisDefinition) {

    // make sure componentToSweepID is actually in the circuit netlist
    for (int j = 0; j < transientAnalysisDefinition.getDrivers().length; j++) {
      SimulationPreCheck.verifyComponentToSweepOrDriveId(netlist, transientAnalysisDefinition.getDrivers()[j].getId());
    }
  }
}
