package po_projekt2.organisms.plants;

import java.awt.Color;
import java.util.Random;
import po_projekt2.Organism;
import po_projekt2.World;
import po_projekt2.organisms.Plant;
import po_projekt2.organisms.animals.CyberSheep;
import po_projekt2.organisms.animals.Human;
import po_projekt2.worldElements.Coordinates;
/**
 *
 * @author Bogna
 */
public class Hogweed extends Plant{
    public Hogweed(int whenWasBorn, Coordinates coords, World world){
        super(10, whenWasBorn, new Color(173,255,47), "Hogweed", coords, world, Organism.typeOfOrganism.HOGWEED, true);
    }

    @Override
    public void action() {
	this.setDirections(1);
        Coordinates[] c = new Coordinates[4];
	Coordinates left = new Coordinates(coords.getX() + 1, coords.getY());
        Coordinates right = new Coordinates(coords.getX() - 1, coords.getY());
        Coordinates up = new Coordinates(coords.getX(), coords.getY()- 1);
        Coordinates down = new Coordinates(coords.getX(), coords.getY() + 1);
        c[0] = left;
        c[1] = right;
        c[2] = up;
        c[3] = down;
	for (int i = 0; i < 4; i++) {
            if (directions[i] == true) {
                Organism tmp = world.getBoard().getOrganism(c[i].getX(), c[i].getY());
                if (tmp != null && tmp.getIsAnimal() && !(tmp instanceof CyberSheep)) {
                    if (tmp instanceof Human) {
                        world.setIsHumanAlive(false);
                    }
                    world.getBoard().removeOrganism(tmp.getCoords());
                    this.getWorld().deleteOrganism(tmp);
                    world.addComment(this.getOrganismName() + "[x = " + this.getCoords().getX() + "; y = " + this.getCoords().getY() + "] kills " + tmp.getOrganismName() + "[x = " + tmp.getCoords().getX() + "; y = " + tmp.getCoords().getY() + "].\n");
		}
            }
	}
        Random rand = new Random();
	int n = rand.nextInt(50);
	if (n >= 45) {
		spread();
	}
    }
    
    @Override
    public void specialColision(Organism other, int x, int y, boolean isAttacking) {
        if(!(other instanceof CyberSheep)){
            if (other instanceof Human) {
                world.setIsHumanAlive(false);
            }
            world.getBoard().removeOrganism(other.getCoords());
            other.getWorld().deleteOrganism(other);
            world.addComment(this.getOrganismName() + "[x = " + this.getCoords().getX() + "; y = " + this.getCoords().getY() + "] kills " + other.getOrganismName() + "[x = " + other.getCoords().getX() + "; y = " + other.getCoords().getY() + "].\n");
        }else{
            world.getBoard().removeOrganism(this.getCoords());
            other.getWorld().deleteOrganism(this);
            Coordinates c = other.getCoords();
            other.setCoords(x,y);
            other.getWorld().getBoard().moveOrganism(other, c);
            world.addComment(other.getOrganismName() + "[x = " + other.getCoords().getX() + "; y = " + other.getCoords().getY() + "] kills " + this.getOrganismName() + "[x = " + this.getCoords().getX() + "; y = " + this.getCoords().getY() + "].\n");
        }
    }
}
