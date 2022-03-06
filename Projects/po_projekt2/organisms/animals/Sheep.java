package po_projekt2.organisms.animals;

import java.awt.Color;

import po_projekt2.World;
import po_projekt2.organisms.Animal;
import po_projekt2.worldElements.Coordinates;
import po_projekt2.Organism.typeOfOrganism;
/**
 *
 * @author Bogna
 */
public class Sheep extends Animal{
    
    public Sheep(int  whenWasBorn, Coordinates coords, World world){
        super(4, 4, whenWasBorn, 1, Color.LIGHT_GRAY, "Sheep", coords, world, typeOfOrganism.SHEEP, false);
    }
}
