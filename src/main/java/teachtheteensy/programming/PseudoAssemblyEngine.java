package teachtheteensy.programming;

import org.reflections.Reflections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Moteur de simulation du code de la Teensy
 */
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

    /**
     * Le temps écoulé depuis le lancement du moteur (en temps de jeu)
     */
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

    /**
     * Liste des instructions reconnues
     * @see InstructionTag
     */
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
                    throw new RuntimeException("Bah alors, on a pas mis de constructeur sans arguments?", e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Bah alors, on a pas mis de constructeur public?", e);
                } catch (ClassCastException e) {
                    throw new RuntimeException("Bah alors, c'est pas une instruction cette classe!", e);
                }
            }
        }
    }

    public static Map<String, Instruction> getInstructionTable() {
        if(instructionTable.isEmpty()) {
            initInstructionTable(); // recherche des instructions si besoin
        }
        return instructionTable;
    }

    /**
     * Crées un nouveau moteur de simulation avec le code source donné
     * @param sourceCode le code source
     */
    public PseudoAssemblyEngine(String sourceCode) {
        getInstructionTable();
        // initialisation de l'état du moteur
        this.state = State.RUNNING;
        this.code = sourceCode;
        this.lines = sourceCode.split("\n");
        this.programCounter = 0;
    }

    /**
     * Exécute une simple étape du moteur.
     * Si l'état est {@link State#PAUSED} ou {@link State#CRASHED}, le moteur ne fait d'incrémenter son compteur de temps
     * Si l'état est {@link State#DELAY}, le moteur vérifie si le temps d'attente a été effectué, et s'il l'est, remets l'état à {@link State#RUNNING}
     * Enfin, si l'état est {@link State#RUNNING} ou vient de devenir {@link State#RUNNING} suite à la fin d'une attente,
     * le moteur exécute autant d'instructions que possible autorisé pendant 'delta' par {@link #getLineExecutionTime()}
     * @param delta le temps écoulé depuis le dernier appel
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
        } else {
            lastLineExecutionTime = time;
        }
    }

    /**
     * Exécutes une ligne si possible (ie pas de crash ou qu'il reste assez de temps)
     * @return 'true' si l'exécution a été possible
     */
    private boolean runLine() {
        if(time-lastLineExecutionTime >= lineExecutionTime) {
            if(programCounter >= lines.length) {
                state = State.CRASHED;
                errorMessage = "On exécute du code en dehors de la mémoire réservée!";
                return false;
            }

            while(lines[programCounter].isEmpty() || lines[programCounter].startsWith("#")) {
                programCounter++;
                if(programCounter >= lines.length) {
                    state = State.CRASHED;
                    errorMessage = "On exécute du code en dehors de la mémoire réservée!";
                    return false;
                }
            }
            String line = lines[programCounter];
            CodeVerifier.VerificationResult result = CodeVerifier.verifyLine(line);
            String[] parts = line.split(" ");
            String command = parts[0];
            Instruction instruction = instructionTable.get(command.toLowerCase());
            switch (result) {
                case INVALID_INSTRUCTION:
                    crash("Instruction non valide");
                    return false;

                case INVALID_ARGUMENT_COUNT:
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

    /**
     * Mets le moteur dans l'état {@link State#CRASHED} avec un message d'erreur
     * @param message message d'erreur
     */
    private void crash(String message) {
        state = State.CRASHED;
        errorMessage = message;
    }

    /**
     * Mets le moteur dans l'état {@link State#DELAY} avec un temps d'attente donné
     * @param delay temps d'attente
     */
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
