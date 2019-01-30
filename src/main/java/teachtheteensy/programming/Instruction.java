package teachtheteensy.programming;

public interface Instruction {
    String name();
    int argumentCount();
    void execute(String[] fullCommand, PseudoAssemblyEngine engine);
}
