package teachtheteensy.electricalcomponents;

import javafx.scene.image.Image;
import teachtheteensy.Assets;

public class Led extends ElectricalComponent {

    private final Pin plusPin;
    private final Pin minusPin;
    private boolean isOn;

    public Led(){
        super(Assets.getImage("components/led_eteinte.png"), 99/2.0, 378/2.0); // /2.0 pour diviser par 2 la taille du composant par rapport à sa texture
        plusPin = new Pin(this,"+",-1,71/2.0,315/2.0);
        minusPin = new Pin(this,"-",-1,34/2.0,378/2.0);
        pins.add(plusPin);
        pins.add(minusPin);
    }

    @Override
    public Image getTexture() {
        return isOn ? Assets.getImage("components/led_rouge.png") : super.getTexture();
    }

    @Override
    public void step() {
        super.step();
        isOn =  ! plusPin.getConnections().isEmpty() &&  ! minusPin.getConnections().isEmpty();
    }

    @Override
    public ElectricalComponent clone() {
        return new Led();
    }
}
