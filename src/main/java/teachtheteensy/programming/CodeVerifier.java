package teachtheteensy.programming;

import java.util.Map;

public class CodeVerifier {

    public enum VerificationResult {
        OK(null),
        INVALID_INSTRUCTION("Instruction non valide"),
        INVALID_ARGUMENT_COUNT("Mauvais nombre d'arguments"),

        ;

        private String message;

        VerificationResult(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public static VerificationResult verifyLine(String line) {
        if(line.isEmpty())
            return VerificationResult.OK;
        if(line.startsWith("#")) // commentaires
            return VerificationResult.OK;
        Map<String, Instruction> instructionTable = PseudoAssemblyEngine.getInstructionTable();
        String[] parts = line.split(" ");
        String command = parts[0];
        Instruction instruction = instructionTable.get(command.toLowerCase());
        if(instruction == null) {
            return VerificationResult.INVALID_INSTRUCTION;
        }
        if(instruction.argumentCount() != parts.length-1) {
            return VerificationResult.INVALID_ARGUMENT_COUNT;
        }
        return VerificationResult.OK;
    }
}
