import org.junit.Assert;
import org.junit.Test;
import teachtheteensy.programming.InstructionTag;
import teachtheteensy.programming.PseudoAssemblyEngine;

public class TestAssemblyEngine {

    private PseudoAssemblyEngine engine;

    @Test
    public void delayState() {
        engine = new PseudoAssemblyEngine("delay 500");
        engine.step(engine.getLineExecutionTime()); // 0.1 total
        Assert.assertEquals(engine.getState(), PseudoAssemblyEngine.State.DELAY);
        engine.step(0.3); // 0.4 total
        Assert.assertEquals(engine.getState(), PseudoAssemblyEngine.State.DELAY);
        engine.step(0.2); // 0.5 total, should be finished
        Assert.assertEquals(engine.getState(), PseudoAssemblyEngine.State.RUNNING);
    }

    @Test
    public void dataFlowLeak() {
        engine = new PseudoAssemblyEngine("nop");
        engine.step(engine.getLineExecutionTime()); // execute a single line

        Assert.assertEquals(engine.getState(), PseudoAssemblyEngine.State.RUNNING);

        engine.step(engine.getLineExecutionTime()); // execute a single line, should fall out
        Assert.assertEquals(engine.getState(), PseudoAssemblyEngine.State.CRASHED);
        Assert.assertEquals(engine.getErrorMessage(), "On exécute du code en dehors de la mémoire réservée!");
    }

}
