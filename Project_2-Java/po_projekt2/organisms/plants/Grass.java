package po_projekt2.organisms.plants;

import java.awt.Color;
import po_projekt2.World;
import po_projekt2.organisms.Plant;
import po_projekt2.worldElements.Coordinates;

/**
 *
 * @author Bogna
 */
public class Grass extends Plant{
    
    public Grass(int whenWasBorn, Coordinates coords, World world){
        super(0, whenWasBorn, new Color(0,255,127), "Grass", coords, world, typeOfOrganism.GRASS, false);
    }
}
