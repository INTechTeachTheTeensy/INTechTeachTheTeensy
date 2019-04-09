package teachtheteensy.minigames;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import teachtheteensy.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Example de code mini-jeu, très simpliste<br/>
 * Pas besoin de mettre toutes les classes dans la classe de leur mini-jeu, c'est vraiment pour simplifier la lecture/minimiser le nombre de fichiers pour cet exemple
 * <br/>
 * <br/>
 * Ce que cette exemple montre:
 * <br/>
 * - Comment dessiner des éléments à l'écran.<br/>
 * - Comment dessiner du texte à l'écran.<br/>
 * - Comment mettre à jour l'état du jeu.<br/>
 * - Comment gérer un clic.<br/>
 * - Comment gérer le clavier.<br/>
 */
public class Snake extends Minigame {

    /**
     * Liste de ce qu'il peut y avoir dans une case
     */
    enum SlotType {
        EMPTY, SNAKE, FRUIT;
    }

    /**
     * Direction de déplacement du serpent
     */
    enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE/* valeur de départ*/
    }

    /**
     * Point 2D
     */
    class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // Auto-généré par IDEA
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        // Auto-généré par IDEA
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    // Dimensions de la grille de jeu
    // 16 et 9 pour avoir un aspect 16:9 (16 9ième) pour s'adapter aux écrans classiques
    // c'est juste pour faire joli
    private static final int WIDTH = 16*2;
    private static final int HEIGHT = 9*2;

    /**
     * Taille, en pixels d'une cellule de la grille
     */
    private static final int CELL_SIZE = 40;

    /**
     * Mise à jour du jeu (donc déplacement) tous les 10 ticks (~60/10 Hz => ~6Hz)
     */
    private static final int TICKS_PER_UPDATE = 10;

    // === INFORMATIONS DU SERPENT ===
    /**
     * Permet de compter le score
     */
    private int snakeLength;

    /**
     * Position de la tête du serpent
     */
    private Point headPos;

    /**
     * Morceaux du serpent
     */
    private final List<Point> snakeParts = new ArrayList<>();

    /**
     * La direction actuelle de déplacement du serpent
     */
    private Direction currentDirection = Direction.NONE;

    /**
     * La prochaine direction à appliquer au serpent
     */
    private Direction newDirection = Direction.NONE;
    // ================================

    /**
     * Position du fruit
     */
    private Point fruitPos;

    /**
     * Grille de jeu
     */
    private final SlotType[][] grid = new SlotType[WIDTH][HEIGHT];

    /**
     * Compteur de tick. Permet de manière assez peu propre de régler la vitesse du jeu
     */
    private int tick;

    /**
     * As-t-on perdu?
     */
    private boolean gameOver;

    /**
     * Couleur semi-transparente pour afficher un fond translucent rouge devant la zone de jeu en cas de game over
     */
    private Color dimBackground = new Color(1,0.0,0.0,0.5);

    public Snake() {
        reset();
    }

    @Override
    public void tick() {
        if(gameOver)
            return; // on ne bouge pas le jeu s'il est perdu
        tick++;
        if(tick % TICKS_PER_UPDATE != 0) { // contrôle de la vitesse du jeu
            return;
        }

        currentDirection = newDirection;

        // calcul de la nouvelle position de la tête
        int newX = headPos.x;
        int newY = headPos.y;
        switch (currentDirection) {
            case LEFT:
                newX--;
                break;

            case RIGHT:
                newX++;
                break;

            case UP:
                newY--;
                break;

            case DOWN:
                newY++;
                break;
        }

        // vérification si on sort de la zone de jeu
        if(newX < 0 || newX >= WIDTH
        || newY < 0 || newY >= HEIGHT) {
            gameOver = true;
            return;
        }

        Point newHeadPos = new Point(newX, newY);
        if(currentDirection != Direction.NONE) {
            // vérification qu'on se mange pas soit-même
            boolean intersect = snakeParts.stream().anyMatch(it -> it.equals(newHeadPos));
            if(intersect) {
                gameOver = true;
                return;
            }

            // déplacement du serpent
            headPos = newHeadPos;
            snakeParts.add(0, newHeadPos);

            if(fruitPos.equals(headPos)) {
                generateFruit();
                snakeLength++;
            } else {
                // si on ne mange pas le fruit, on libère la dernière case du serpent (il s'est déplacé)
                Point last = snakeParts.remove(snakeLength);
                grid[last.x][last.y] = SlotType.EMPTY;
            }

            grid[newHeadPos.x][newHeadPos.y] = SlotType.SNAKE;
        }

    }

    @Override
    public void render(GraphicsContext ctx) {
        /*
        Le rendu se fait selon l'algorithme du peintre:
        ce qui est dessiné en premier (dans le code) est ce qui est au fond.
        Si vous redessinez une zone de l'écran après l'avoir dessiné auparavant, vous remplacez le contenu.

        Si vous voulez avoir de la 'profondeur', dessinez ce qui est le plus à l'avant en dernier





        A savoir également: (0,0) est le coin haut gauche et les Y croissants vont vers le bas de l'écran)
         */

        // remplit l'écran de noir (permet d'effacer l'image de la frame d'avant)
        ctx.setFill(Color.BLACK);
        ctx.fillRect(0,0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());

        // set de la couleur de tracé (par opposition à 'setFill' qui détermine la couleur de remplissage)
        ctx.setStroke(Color.GRAY);

        // dessin de la grille et de son contenu
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                SlotType type = grid[x][y];
                switch (type) {
                    case SNAKE:
                        // on sélectionne la couleur et on remplit la cellule
                        ctx.setFill(Color.GREEN);
                        ctx.fillRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        break;

                    case FRUIT:
                        // idem.
                        ctx.setFill(Color.BLUE);
                        ctx.fillRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        break;
                }

                if(x == headPos.x && y == headPos.y) { // dessin des yeux du serpent
                    ctx.save(); // on sauvegarde l'état actuel du contexte

                    // === transformations pour ne penser qu'à dessiner les yeux en haut de la case et faire une rotation pour adapter à la direction de déplacement
                    ctx.translate(headPos.x*CELL_SIZE + CELL_SIZE/2.0, headPos.y*CELL_SIZE + CELL_SIZE/2.0);
                    switch (currentDirection) {
                        case UP:
                        case NONE: // NONE et UP auront le même rendu
                            ctx.rotate(0.0); // position par défaut
                            break;

                        case DOWN:
                            ctx.rotate(180.0);
                            break;

                        case LEFT:
                            ctx.rotate(-90);
                            break;

                        case RIGHT:
                            ctx.rotate(90.0);
                            break;
                    }

                    ctx.setFill(Color.DARKGRAY);
                    final double eyeSize = CELL_SIZE/3.0;
                    ctx.fillRect(-CELL_SIZE/2.0, -CELL_SIZE/2.0, eyeSize, eyeSize);
                    ctx.fillRect(-CELL_SIZE/2.0+CELL_SIZE-eyeSize, -CELL_SIZE/2.0, eyeSize, eyeSize);

                    ctx.restore(); // on restaure l'état du contexte
                }

                // on dessine la cellule par dessus
                // pas besoin de rechanger la couleur de tracé: on ne l'a pas changé depuis avant la boucle
                ctx.strokeRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // Affichage du score et des commandes
        ctx.setFill(Color.WHITE);
        ctx.fillText("Score: "+snakeLength, WIDTH*CELL_SIZE, 25);
        ctx.fillText("=== Commandes ===", WIDTH*CELL_SIZE, 25+25);
        ctx.fillText("Espace: Reset", WIDTH*CELL_SIZE, 25+25*2);
        ctx.fillText("Clic (gauche ou droite): Reset", WIDTH*CELL_SIZE, 25+25*3);
        ctx.fillText("Touches flêchées: Changement de direction du serpent", WIDTH*CELL_SIZE, 25+25*4);

        // Affichage du message de Game Over
        if(gameOver) {
            ctx.setFill(dimBackground); // rend le fond légèrement rouge
            ctx.fillRect(0, 0, Game.getInstance().getScreenWidth(), Game.getInstance().getScreenHeight());

            ctx.setFill(Color.RED);
            ctx.fillRect(Game.getInstance().getScreenWidth()/2.0 - 400, Game.getInstance().getScreenHeight()/2.0 - 100, 800, 200);

            ctx.setFill(Color.WHITE);
            ctx.fillText("GAME OVER", Game.getInstance().getScreenWidth()/2.0, Game.getInstance().getScreenHeight()/2.0);
        }
    }

    // Les deux clics relancent le jeu (c'est pour montrer comment utiliser les clics)

    @Override
    public void leftClick(double sceneX, double sceneY) {
        reset();
    }

    @Override
    public void rightClick(double sceneX, double sceneY) {
        reset();
    }

    /**
     * Initialisation et reset du jeu
     */
    private void reset() {
        // reset de la grille
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                grid[x][y] = SlotType.EMPTY;
            }
        }

        // choix de la position de la tête du serpent
        int startX = WIDTH/2;
        int startY = HEIGHT/2;
        grid[startX][startY] = SlotType.SNAKE;
        headPos = new Point(startX, startY);
        snakeParts.clear();
        snakeParts.add(headPos);

        // on génère un fruit
        generateFruit();

        // reset du score
        snakeLength = 1;
        currentDirection = Direction.NONE;
        gameOver = false;
    }

    private void generateFruit() {
        boolean intersect;
        do {
            int fruitX = (int) (Math.random()*WIDTH);
            int fruitY = (int) (Math.random()*HEIGHT);
            fruitPos = new Point(fruitX, fruitY);

            intersect = snakeParts.stream().anyMatch(it -> it.equals(fruitPos));
        } while(intersect); // si le fruit est sur le serpent, on reprend un autre point
        grid[fruitPos.x][fruitPos.y] = SlotType.FRUIT;
    }

    /**
     * Appelé quand on commence à appuyer sur la touche sur le clavier
     * @param event Les infos sur la touche
     */
    @Override
    public void keyPressed(KeyEvent event) {
        // permet de changer la direction du serpent en fonction de la touche pressée
        switch (event.getCode()) {
            case DOWN:
                if(currentDirection != Direction.UP) {
                    newDirection = Direction.DOWN;
                }
                break;

            case UP:
                if(currentDirection != Direction.DOWN) {
                    newDirection = Direction.UP;
                }
                break;

            case LEFT:
                if(currentDirection != Direction.RIGHT) {
                    newDirection = Direction.LEFT;
                }
                break;

            case RIGHT:
                if(currentDirection != Direction.LEFT) {
                    newDirection = Direction.RIGHT;
                }
                break;

            case SPACE:
                reset();
                break;
        }
    }

    /**
     * Appelé quand on relache la touche sur le clavier
     * @param event Les infos sur la touche
     */
    @Override
    public void keyReleased(KeyEvent event) {
    }
}
