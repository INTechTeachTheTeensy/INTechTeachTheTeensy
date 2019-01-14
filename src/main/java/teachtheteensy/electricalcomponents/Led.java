package teachtheteensy.electricalcomponents;

import teachtheteensy.Assets;

public class Led extends ElectricalComponent {
    public Led(){
        super(Assets.getImage("components/led_eteinte.png"));
        pins.add(new Pin(this,"plus",-1,71,315));
        pins.add(new Pin(this,"moins",-1,34,378));

    }

    @Override
    public ElectricalComponent clone() {
        return new Led();
    }
}
