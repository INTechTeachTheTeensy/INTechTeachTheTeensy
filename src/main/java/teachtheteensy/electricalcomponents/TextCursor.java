package teachtheteensy.electricalcomponents;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import teachtheteensy.Renderable;

import java.util.List;

public class TextCursor implements Renderable {
    private final double maxLineWidth;
    private List<String> lines;
    private double fontWidth;
    private double fontHeight;
    private int line = 0;
    private int column = 0;

    public TextCursor(List<String> lines, double totalWidth, double fontWidth, double fontHeight) {
        this.lines = lines;
        this.fontWidth = fontWidth;
        this.fontHeight = fontHeight;
        this.maxLineWidth = totalWidth/fontWidth;
    }

    public void goLeft() {
        column--;
        // si on appuie sur gauche alors qu'on est tout à gauche il faut remonter d'une ligne
        if(column < 0) {
            line--;
            column = 0;
            if(line < 0) {
                line = 0;
            } else {
                column = lines.get(line).length();
            }
        }
    }

    public void goRight() {
        column++;
        if(column > lines.get(line).length()) {
            column = 0;
            line++;
            if(line >= lines.size()) {
                line = lines.size()-1;
                column = lines.get(line).length();
            }
        }
    }

    public void goUp() {
        line--;
        if(line < 0) {
            line = 0;
            column = 0;
        }

        if(column > lines.get(line).length()) {
            column = lines.get(line).length();
        }
    }

    public void goDown() {
        line++;
        if(line >= lines.size()) {
            line = lines.size()-1;
            column = lines.get(line).length();
        }

        if(column > lines.get(line).length()) {
            column = lines.get(line).length();
        }
    }

    @Override
    public void render(GraphicsContext ctx) {
        ctx.setFill(Color.RED);
        ctx.fillRect(column*fontWidth, line*fontHeight, fontWidth, fontHeight);
    }

    public void insert(String text) {
        System.out.println("[TextCursor] insert "+text);
        if(currentLine().length()+text.length() > maxLineWidth) {
            return;
        }
        if(column == currentLine().length()) { // si on est à la fin de la ligne
            lines.set(line, currentLine()+text);
        } else if(column == 0) {
            lines.set(line, text+currentLine());
        } else {
            lines.set(line, currentLine().substring(0, column+1)+text+currentLine().substring(column+1));
        }
        column += text.length();
    }

    private String currentLine() {
        return lines.get(line);
    }

    private String lineAbove() {
        if(line > 0)
            return lines.get(line-1);
        return lines.get(line);
    }

    private String lineBelow() {
        if(line < lines.size()-1)
            return lines.get(line+1);
        return lines.get(line);
    }

    public void backSpace() {
        if(column == 0) { // on fusionne les deux lignes
            if(line > 0) { // si on est sur la première ligne, on peut pas fusionner!
                String previousLine = lineAbove();
                String merge = previousLine+currentLine();
                lines.remove(line); // retire la ligne courante
                lines.remove(line-1); // retire la ligne au dessus
                lines.add(line-1, merge);
                line = line-1; // on va sur la ligne précédente
                column = previousLine.length();
            }
        } else {
            lines.set(line, currentLine().substring(0, column-1)+currentLine().substring(column));
            column--;
        }
    }

    public void enter() {
        if(column == 0) { // on ajoute une ligne vide
            lines.add(line, "");
            line++;
        } else {
            String onThisLine = currentLine().substring(0, column);
            String onNextLine = currentLine().substring(column);
            lines.set(line, onThisLine);
            lines.add(line+1, onNextLine);
            line++;
        }
        column = 0;
    }

    public void delete() {
        if(column == currentLine().length()) {
            if(line < lines.size()-1) { // si on est sur la dernière ligne, on ne peut rien faire
                String merge = currentLine()+lineBelow();
                lines.remove(line+1); // retrait de la ligne d'en dessous
                lines.remove(line); // retrait de la ligne courante
                lines.add(line, merge);
            }
        } else {
            String beforeCursor = currentLine().substring(0, column);
            String afterCursor = currentLine().substring(column+1);
            lines.set(line, beforeCursor+afterCursor);
        }
    }

    public void goToBegin() {
        column = 0;
    }

    public void goToEnd() {
        column = currentLine().length();
    }
}
