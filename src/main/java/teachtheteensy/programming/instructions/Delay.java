package teachtheteensy.programming.instructions;

import teachtheteensy.programming.Instruction;
import teachtheteensy.programming.InstructionTag;
import teachtheteensy.programming.PseudoAssemblyEngine;

@InstructionTag
public class Delay implements Instruction {
    @Override
    public String name() {
        return "delay";
    }

    @Override
    public int argumentCount() {
        return 1;
    }

    @Override
    public void execute(String[] fullCommand, PseudoAssemblyEngine engine) {
        engine.delay(Long.parseLong(fullCommand[1])/1000.0);
    }
}
