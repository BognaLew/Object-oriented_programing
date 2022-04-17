package po_projekt2.organisms.plants;

import java.awt.Color;
import java.util.Random;
import po_projekt2.World;
import po_projekt2.organisms.Plant;
import po_projekt2.worldElements.Coordinates;

/**
 *
 * @author Bogna
 */
public class Milt extends Plant{
    public Milt(int whenWasBorn, Coordinates coords, World world){
        super(0, whenWasBorn, Color.YELLOW, "Milt", coords, world, typeOfOrganism.MILT, false);
    }

    @Override
    public void action() {
	for (int i = 0; i < 3; i++) {
            Random rand = new Random();
            int n = rand.nextInt(50);
            if (n >= 45) {
                spread();
            }
	}
    }
}
