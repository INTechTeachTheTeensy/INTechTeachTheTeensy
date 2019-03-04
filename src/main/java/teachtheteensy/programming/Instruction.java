package teachtheteensy.programming;

/**
 * Représentation d'une instruction pour la Teensy. Donne son nom, son nombre d'arguments et son comportement
 */
public interface Instruction {
    String name();
    int argumentCount();
    void execute(String[] fullCommand, PseudoAssemblyEngine engine);
}
