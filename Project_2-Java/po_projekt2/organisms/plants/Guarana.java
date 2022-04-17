package po_projekt2.organisms.plants;

import java.awt.Color;
import po_projekt2.Organism;
import po_projekt2.World;
import po_projekt2.organisms.Plant;
import po_projekt2.worldElements.Coordinates;

/**
 *
 * @author Bogna
 */
public class Guarana extends Plant{
    public Guarana(int whenWasBorn, Coordinates coords, World world){
        super(0, whenWasBorn, Color.RED, "Guarana", coords, world, Organism.typeOfOrganism.GUARANA, true);
    }

    @Override
    public void specialColision(Organism other, int x, int y, boolean isAttacking) {
	other.setStrenght(other.getStrenght() + 3);
	other.getWorld().deleteOrganism(this);
	Coordinates c1 = other.getCoords();
	other.setCoords(x, y);
	world.getBoard().moveOrganism(other, c1);
        world.addComment(other.getOrganismName() + "[x = " + other.getCoords().getX() + "; y = " + other.getCoords().getY() + "] kills " + this.getOrganismName() + "[x = " + this.getCoords().getX() + "; y = " + this.getCoords().getY() + ".\n"
                                            + other.getOrganismName() + " gains +3 to strenght. It now eguals " + other.getStrenght() + ".\n");
    }
}
