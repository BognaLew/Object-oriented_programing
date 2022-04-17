package po_projekt2.organisms.plants;

import java.awt.Color;
import po_projekt2.Organism;
import po_projekt2.World;
import po_projekt2.organisms.Plant;
import po_projekt2.organisms.animals.Human;
import po_projekt2.worldElements.Coordinates;
/**
 *
 * @author Bogna
 */
public class Belladonna extends Plant{
    
    public Belladonna(int whenWasBorn, Coordinates coords, World world){
        super(99, whenWasBorn, new Color(25,25,112), "Belladonna", coords, world, typeOfOrganism.BELLADONNA, true);
    }

    @Override
    public void specialColision(Organism other, int x, int y, boolean isAttacking) {
	if (other instanceof Human) {
            world.setIsHumanAlive(false);
	}
	world.getBoard().removeOrganism(other.getCoords());
	world.deleteOrganism(other);
        world.addComment(this.getOrganismName() + "[x = " + this.getCoords().getX() + "; y = " + this.getCoords().getY() + "] kills " + other.getOrganismName() + "[x = " + other.getCoords().getX() + "; y = " + other.getCoords().getY() + ".\n");
    }
}
