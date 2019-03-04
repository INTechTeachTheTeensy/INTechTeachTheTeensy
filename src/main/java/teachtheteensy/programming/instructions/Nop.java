package teachtheteensy.programming.instructions;

import teachtheteensy.programming.Instruction;
import teachtheteensy.programming.InstructionTag;
import teachtheteensy.programming.PseudoAssemblyEngine;

@InstructionTag
/**
 * Représentation l'instruction 'nop' qui fait un cycle sans rien faire
 */
public class Nop implements Instruction {
    @Override
    public String name() {
        return "nop";
    }

    @Override
    public int argumentCount() {
        return 0;
    }

    @Override
    public void execute(String[] fullCommand, PseudoAssemblyEngine engine) {}
}
