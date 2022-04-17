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
public class Wolf extends Animal{
    
    public Wolf(int  whenWasBorn, Coordinates coords, World world){
        super(9, 5, whenWasBorn, 1, Color.DARK_GRAY, "Wolf",coords, world, typeOfOrganism.WOLF, false);
    }
    
//    @Override
//    public String getOrganismName() {
//	return "Wolf ";
//    }
}
