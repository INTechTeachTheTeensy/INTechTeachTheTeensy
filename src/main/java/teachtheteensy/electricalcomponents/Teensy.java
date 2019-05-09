package teachtheteensy.electricalcomponents;

import com.google.common.collect.Collections2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.knowm.jspice.netlist.NetlistComponent;
import org.knowm.jspice.netlist.NetlistDCVoltage;
import teachtheteensy.Assets;
import teachtheteensy.Game;
import teachtheteensy.electricalcomponents.simulation.NetlistTeensyPin;
import teachtheteensy.electricalcomponents.simulation.NodeMap;
import teachtheteensy.math.OffsetRectangle;
import teachtheteensy.programming.CodeVerifier;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Représentation d'une Teensy avec ses pins
 */
public class Teensy extends ElectricalComponent {

    private static final Color HOVERED_BACKGROUND_COLOR = new Color(0.5, 0.5, 0.5, 0.5);
    private static final Color HOVERED_TEXT_COLOR = new Color(1, 1, 1, 0.5);

    /**
     * Liste des componsants JSpice correspondants aux pins
     */
    private final List<NetlistComponent> netlistPins;

    /**
     * Map pour récupérer rapidement le composant JSpice en fonction du nom d'une pin
     */
    private final Map<String, NetlistTeensyPin> pinMap;
    private final OffsetRectangle codeArea;
    private final double textWidth;

    private List<String> codeLines = new LinkedList<>();

    /**
     * Résultats des vérifications de chacune des lignes
     */
    private List<CodeVerifier.VerificationResult> verifications = new LinkedList<>();

    /**
     * Est-ce qu'on essaie d'écrire sur la Teensy?
     */
    private boolean focused;

    private double fontHeight = 12.0;
    private TextCursor cursor;

    private Font font = Assets.getConsolasFont(15);

    public Teensy() {
        super(Assets.getImage("components/teensy.png"));

        final float xMargin = 15f;
        final float yMargin = 10f;
        codeArea = new OffsetRectangle(box, xMargin, yMargin, box.getWidth()-2*xMargin, box.getHeight()-yMargin);
        pinMap = new HashMap<>();

        Text t = new Text("A");
        t.setFont(font);
        t.applyCss();
        textWidth = t.getLayoutBounds().getWidth();
        cursor = new TextCursor(codeLines, codeArea.getWidth(), textWidth, fontHeight);

        // initialisation des pins
        pins.add(new GNDPin(this, -1, 9, 22.5));
        for (int i = 0; i <= 12; i++) {
            pins.add(new Pin(this, ""+i, i, 9, 22.5+(i+1)*22.5));
        }
        pins.add(new VoltagePin(this,"+3.3", 3.3 ,-1,9,22.5*15));
        for (int i = 24; i <= 32; i++) {
            pins.add(new Pin(this, ""+i, i, 9, 22.5+(i-9)*22.5));
        }
        pins.add(new Pin(this, "Vin",-1,145,22.5));
        pins.add(new GNDPin(this, -1,145,22.5*2));
        pins.add(new Pin(this, "+3.3",-1,145,22.5*3));
        for (int i = 23; i >= 13; i--) {
            pins.add(new Pin(this, ""+i, i, 145, 22.5+(26-i)*22.5));
        }
        pins.add(new GNDPin(this, -1,145,22.5*15));
        pins.add(new Pin(this, "A22",-1,145,22.5*16));
        pins.add(new Pin(this, "A21",-1,145,22.5*17));
        for (int i = 39; i >= 33; i--) {
            pins.add(new Pin(this, ""+i, i, 145, 22.5+(56-i)*22.5));
        }

        // liste des équivalents des pins pour JSpice
        netlistPins = pins.stream()
                .map(NetlistTeensyPin::new)
                .distinct() // distinct() est utilisé pour n'avoir que des pins de nom différents
                .map(p -> {
                    pinMap.put(p.getPin().getName(), p);
                    return p;
                })
                .collect(Collectors.toList());

        codeLines.add("DELAY 500");
        codeLines.add("NOP");
        codeLines.add("HELLO");
        codeLines.add("HIGH 13");
     //   netlistPins.add(new NetlistDCVoltage(toString()+"("+hashCode()+").3.3->GND", 3.3, pinMap.get("+3.3").getId(), "0"));
    }

    @Override
    public ElectricalComponent clone() {
        return new Teensy();
    }

    @Override
    public void resetComponent() {
        focused = false;
    }

    @Override
    public void step() {
        super.step();
        if(pinMap.containsKey("+3.3"))
            pinMap.get("+3.3").getSource().setValue(3.3);
    }

    @Override
    public void render(GraphicsContext ctx) {
        super.render(ctx);

        ctx.setEffect(null);
        if(focused || codeArea.isPointIn(Game.getInstance().getMouseX(), Game.getInstance().getMouseY())) {
            Color backgroundColor = Color.GRAY;
            Color textColor = Color.WHITE;
            if( ! focused) { // on fait que survoler le composant
                backgroundColor = HOVERED_BACKGROUND_COLOR;
                textColor = HOVERED_TEXT_COLOR;
            }
            ctx.setFill(backgroundColor);
            ctx.fillRect(codeArea.getX(), codeArea.getY(), codeArea.getWidth(), codeArea.getHeight());
            ctx.setFill(textColor);

            ctx.setFont(font);
            double codeLineOffset = 0.0;
            for (int i = 0; i < codeLines.size(); i++) {
                ctx.setFill(textColor);
                String line = codeLines.get(i);
                ctx.fillText(line, codeArea.getX(), codeArea.getY()+fontHeight+codeLineOffset);

                if(verifications.size() > i) {
                    CodeVerifier.VerificationResult result = verifications.get(i);
                    switch (result) {
                        case OK:
                            break;

                        case INVALID_INSTRUCTION:
                        case INVALID_ARGUMENT_COUNT: {
                            double startX = codeArea.getX()+codeArea.getWidth()+10;
                            double startY = codeArea.getY()+codeLineOffset;
                            String message = result.getMessage();

                            ctx.setLineWidth(1.0);
                            ctx.setStroke(Color.RED);
                            ctx.strokeLine(codeArea.getX(), codeArea.getY()+fontHeight+codeLineOffset, codeArea.getX()+textWidth*line.length(),codeArea.getY()+fontHeight+codeLineOffset);

                            ctx.setFill(Color.DARKGRAY);
                            ctx.fillRect(startX, startY, textWidth*message.length(), fontHeight);

                            ctx.setStroke(Color.GRAY);
                            ctx.strokeRect(startX, startY, textWidth*message.length(), fontHeight);

                            ctx.setFill(Color.WHITE);
                            ctx.fillText(message, startX, startY+fontHeight);
                        }
                        break;
                    }
                }

                codeLineOffset += fontHeight;
            }
            // affichage du curseur
            ctx.save();
            ctx.translate(codeArea.getX(), codeArea.getY());
            cursor.render(ctx);
            ctx.restore();
        }
    }

    @Override
    public boolean leftClick(double mouseX, double mouseY) {
        if(box.isPointIn(mouseX, mouseY)) {
            focused = true;
            return true;
        } else {
            focused = false;
            return false;
        }
    }

    @Override
    public boolean keyTyped(KeyEvent event) {
        if( ! focused)
            return false;
        if(event.isControlDown())
            return false;
        switch (event.getCharacter()) {
            case "\n": // entrée
            case "\r": // entrée
                return true;

            case "\b": // retour arrière
                return true;

            default:
                // delete
                if(event.getCharacter().equals(String.valueOf((char)127))) {
                    return true;
                }
        }
        cursor.insert(event.getCharacter().toUpperCase());
        verifyCode();
        return true;
    }

    @Override
    public boolean keyReleased(KeyEvent event) {
        if( ! focused) {
            return false;
        }
        switch (event.getCode()) {
            case LEFT:
                cursor.goLeft();
                verifyCode();
                return true;

            case RIGHT:
                cursor.goRight();
                verifyCode();
                return true;

            case UP:
                cursor.goUp();
                verifyCode();
                return true;

            case DOWN:
                cursor.goDown();
                verifyCode();
                return true;

            case BACK_SPACE:
                cursor.backSpace();
                verifyCode();
                return true;

            case ENTER:
                cursor.enter();
                verifyCode();
                return true;

            case DELETE:
                cursor.delete();
                verifyCode();
                return true;

            case END:
                cursor.goToEnd();
                verifyCode();
                return true;

            case HOME:
                cursor.goToBegin();
                verifyCode();
                return true;

            default:
                return false;
        }
    }

    public void verifyCode() {
        verifications.clear();
        for(String line : codeLines) {
            verifications.add(CodeVerifier.verifyLine(line));
        }
    }

    @Override
    public List<? extends NetlistComponent> toNetlistComponents(NodeMap nodeMap) {
        // mets à jour les noeuds connectés aux pins
        netlistPins.forEach(pin -> {
            if(pin instanceof NetlistTeensyPin) {
                if(((NetlistTeensyPin) pin).getPin() instanceof VoltagePin) {
                    pin.setNodes(new String[] { nodeMap.getNode(((NetlistTeensyPin) pin).getPin()) } );
                } else {
                    pin.setNodes(new String[] { nodeMap.getNode(((NetlistTeensyPin) pin).getPin()), "0" } );
                }
            }
        });

        // retire les pins non connectées
        return netlistPins.stream()
                .filter(p -> {
                    if(p instanceof NetlistTeensyPin) {
                        return ((NetlistTeensyPin) p).getPin().hasAtLeastOneConnection();
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
