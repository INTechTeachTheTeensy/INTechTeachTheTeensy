package teachtheteensy.programming;

import org.reflections.Reflections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PseudoAssemblyEngine {

    public enum State {
        /**
         * Le moteur vient d'exécuter un 'delay', on attend qu'il ait fini avant d'exécuter d'autres instructions
         */
        DELAY,
        /**
         * Le moteur est en pause
         */
        PAUSED,
        /**
         * Le moteur il est cassé :(
         * La Teensy a explosé ou alors on a *vraiment* crashé le moteur
         */
        CRASHED,
        /**
         * Tout va bien, on tourne :)
         */
        RUNNING;
    }

    /**
     * Le """""code""""" qui s'exécute sur la Teensy
     */
    private final String code;
    /**
     * Compteur représentant le temps qu'il reste à attendre (eg avec un DELAY 500)
     */
    private double delayCounter;
    private double time;
    /**
     * Etat actuel du moteur
     */
    private State state;

    private double lineExecutionTime = 0.02500; // 40 instr/s pour le moment
    private double lastLineExecutionTime = 0.0;
    /**
     * Ligne à laquelle le code est actuellement
     */
    private int programCounter;
    /**
     * Lignes de "code"
     */
    private final String[] lines;
    /**
     * Message d'erreur à afficher lorsqu'il y a une erreur
     */
    private String errorMessage;

    private static final Map<String, Instruction> instructionTable = new HashMap<>();

    /**
     * Génère la liste des instructions
     * Ce code permet de ne pas avoir à mettre à jour une liste à la main pour créer de nouvelles instructions
     */
    private static void initInstructionTable() {
        Collection<Class<?>> classes = new Reflections("teachtheteensy.programming.instructions").getTypesAnnotatedWith(InstructionTag.class);
        for(Class<?> clazz : classes) {
            if(Instruction.class.isAssignableFrom(clazz)) {
                try {
                    Instruction instance = (Instruction)clazz.newInstance();
                    instructionTable.put(instance.name(), instance);
                } catch (InstantiationException e) {
                    System.err.println("Bah alors, on a pas mis de constructeur sans arguments?");
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    System.err.println("Bah alors, on a pas mis de constructeur public?");
                    e.printStackTrace();
                } catch (ClassCastException e) {
                    System.err.println("Bah alors, c'est pas une instruction cette classe!");
                    e.printStackTrace();
                }
            }
        }
    }

    public PseudoAssemblyEngine(String sourceCode) {
        if(instructionTable.isEmpty()) {
            initInstructionTable();
        }
        this.state = State.RUNNING;
        this.code = sourceCode;
        this.lines = sourceCode.split("\n");
        this.programCounter = 0;
    }

    /**
     *
     * @param delta le temps écoulé depuis le dernier appel
     * @return
     */
    public void step(double delta) {
        time += delta;

        switch(state) {
            case PAUSED:
            case CRASHED:
                // nop
                break;

            case DELAY:
                delayCounter -= delta;
                if(delayCounter <= 0) {
                    state = State.RUNNING;
                    lastLineExecutionTime = time;
                }
                break;
        }

        if(state == State.RUNNING) {
            while(runLine());
        }
    }

    private boolean runLine() {
        if(time-lastLineExecutionTime >= lineExecutionTime) {
            if(programCounter >= lines.length) {
                state = State.CRASHED;
                errorMessage = "On exécute du code en dehors de la mémoire réservée!";
                return false;
            }

            String line = lines[programCounter];
            String[] parts = line.split(" ");
            String command = parts[0];
            Instruction instruction = instructionTable.get(command.toLowerCase());
            if(instruction == null) {
                crash("Instruction non valide");
                return false;
            }
            if(instruction.argumentCount() != parts.length-1) {
                crash("L'instruction '"+instruction.name()+"' prend "+instruction.argumentCount()+" arguments mais en a reçu "+(parts.length-1));
                return false;
            }
            instruction.execute(parts, this);

            programCounter++;
            lastLineExecutionTime += lineExecutionTime;
            return true;
        }
        return false;
    }

    private void crash(String message) {
        state = State.CRASHED;
        errorMessage = message;
    }

    public void delay(double delay) {
        this.delayCounter = delay;
        state = State.DELAY;
    }

    public State getState() {
        return state;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public double getLineExecutionTime() {
        return lineExecutionTime;
    }
}
