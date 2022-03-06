package po_projekt2.organisms.animals;

import java.awt.Color;
import java.util.Random;
import po_projekt2.Organism;

import po_projekt2.World;
import po_projekt2.organisms.Animal;
import po_projekt2.worldElements.Coordinates;
import po_projekt2.Organism.typeOfOrganism;

/**
 *
 * @author Bogna
 */
public class Antelope extends Animal{
    
    public Antelope(int  whenWasBorn, Coordinates coords, World world){
        super(4, 4, whenWasBorn, 2, new Color(150, 75, 0), "Antelope",coords, world, typeOfOrganism.ANTELOPE, true);
    }
    
    @Override
    public void specialColision(Organism other, int x, int y, boolean isAttacking) {
	if (other instanceof Antelope) {
            this.reproduction(this, (Animal)other);
	}else{
            Random rand = new Random();
            int chance = rand.nextInt(100);
            if (chance >= 50 && other.getIsAnimal()) {
		Coordinates c1 = this.getCoords();
                world.getBoard().removeOrganism(c1);
                if (isAttacking) {
                    this.setCoords(x, y);
		}
		this.setFreeDirections(this.moveRange);
		direction d = this.randDirection();
		
		int x1 = this.getCoords().getX(), y1 = this.getCoords().getY();
		if (d == direction.UP) {
                    y1 -= moveRange;
		}else if (d == direction.DOWN) {
                    y1 += moveRange;
		}else if (d == direction.LEFT) {
                    x1 += moveRange;
		}else if (d == direction.RIGHT) {
                    x1 -= moveRange;
		}
		this.setCoords(x1, y1);
		world.getBoard().moveOrganism(this, c1);
		if (!isAttacking) {
                    Coordinates c2 = other.getCoords();
                    other.setCoords(x, y);
                    world.getBoard().moveOrganism(other, c2);
		}
                world.addComment(this.getOrganismName() + "[x = " + this.getCoords().getX() + "; y = " + this.getCoords().getY() + "] escapes " + other.getOrganismName() + "[x = " + other.getCoords().getX() + "; y = " + other.getCoords().getY() + ".\n");
            }else {
		if (this.strenght >= other.getStrenght()) {
                    if (other instanceof Human) {
			world.setIsHumanAlive(false);
                    }
                    world.getBoard().removeOrganism(other.getCoords());
                    if (isAttacking) {
			Coordinates c1 = this.getCoords();
			this.setCoords(x, y);
			world.getBoard().moveOrganism(this, c1);
                    }
                    world.deleteOrganism(other);
                    world.addComment(this.getOrganismName() + "[x = " + this.getCoords().getX() + "; y = " + this.getCoords().getY() + "] kills " + other.getOrganismName() + "[x = " + other.getCoords().getX() + "; y = " + other.getCoords().getY() + ".\n");
		}else {
                    world.getBoard().removeOrganism(this.coords);
                    other.getWorld().deleteOrganism(this);
                    if (!isAttacking) {
			Coordinates c1 = other.getCoords();
			other.setCoords(x, y);
			world.getBoard().moveOrganism(other, c1);
                    }	
                    world.addComment(other.getOrganismName() + "[x = " + other.getCoords().getX() + "; y = " + other.getCoords().getY() + "] kills " + this.getOrganismName() + "[x = " + this.getCoords().getX() + "; y = " + this.getCoords().getY() + ".\n");
		}
            }
	}
    }
}
