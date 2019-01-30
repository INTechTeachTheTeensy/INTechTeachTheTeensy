import org.knowm.jspice.JSpice;
import org.knowm.jspice.netlist.*;
import org.knowm.jspice.simulate.SimulationPlotData;
import org.knowm.jspice.simulate.SimulationPlotter;
import org.knowm.jspice.simulate.SimulationResult;
import org.knowm.jspice.simulate.transientanalysis.TransientAnalysis;
import org.knowm.jspice.simulate.transientanalysis.TransientConfig;
import org.knowm.jspice.simulate.transientanalysis.driver.Sine;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.math.BigDecimal;

public class DCOPI1R3 {

    public static void main(String[] args) {

        Netlist netlist = new Netlist() {
            {
                addNetListComponent(new NetlistDCVoltage("Vsrc", 0.0, "in", "0"));
                addNetListComponent(new NetlistResistor("Rsrc", 50, "in", "D"));
                addNetListComponent(new NetlistDiode("D1", 3.872e-9, "D", "out"));
                addNetListComponent(new NetlistCapacitor("C1", 0.00001, "out", "0"));
                addNetListComponent(new NetlistResistor("Rload", 1000, "out", "0"));
            }
        };
        TransientConfig transientConfig = new TransientConfig(".02", ".0002", new Sine("Vsrc", 0, "0", 12, "60.0"));
        netlist.setSimulationConfig(transientConfig);
        TransientAnalysis transientAnalysis = new TransientAnalysis(netlist, transientConfig);
        SimulationResult simulationResult;

        XYChart chart = new XYChart(600, 300);

        String[] valuesToPlot = {"V(in)", "V(out)"};
        JFrame frame = new SwingWrapper<>(chart).displayChart();
        BigDecimal dt = new BigDecimal("0.02");
        BigDecimal stepTime = new BigDecimal("0.0003");
        while(true) {
            long time = System.currentTimeMillis();
            simulationResult = transientAnalysis.stepFor(dt, stepTime);
            System.out.println("Time for step: "+(System.currentTimeMillis()-time)+" ms");
            synchronized (chart) {
                chart.setXAxisTitle(simulationResult.getxDataLabel());
                chart.setYAxisTitle(simulationResult.getyDataLabel());
                chart.getSeriesMap().clear();
                for (String valueToPlot : valuesToPlot) {

                    SimulationPlotData simulationData = simulationResult.getSimulationPlotDataMap().get(valueToPlot);

                    if (simulationData == null) {
                        throw new IllegalArgumentException(
                                valueToPlot + " is not a valid node value! Please choose from these values: " + simulationResult.getSimulationPlotDataMap().keySet());
                    }

                    chart.addSeries(valueToPlot, simulationData.getxData(), simulationData.getyData());
                }
            }

            SwingUtilities.invokeLater(() -> {
                synchronized (chart) {
                    frame.getContentPane().repaint();
                }
            });
        }
    }
}
