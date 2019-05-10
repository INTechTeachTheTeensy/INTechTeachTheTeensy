package teachtheteensy.programming.instructions;

import teachtheteensy.programming.Instruction;
import teachtheteensy.programming.InstructionTag;
import teachtheteensy.programming.PinState;
import teachtheteensy.programming.PseudoAssemblyEngine;

@InstructionTag
/**
 * Représentation l'instruction 'delay' qui permet de faire une pause pendant l'exécution du script
 */
public class High implements Instruction {
    @Override
    public String name() {
        return "high";
    }

    @Override
    public int argumentCount() {
        return 1;
    }

    @Override
    public void execute(String[] fullCommand, PseudoAssemblyEngine engine) {
        try {
            engine.pinState(Integer.parseInt(fullCommand[1]), PinState.HIGH);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
