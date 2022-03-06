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
public class Turtle extends Animal{
    
    public Turtle(int  whenWasBorn, Coordinates coords, World world){
        super(3, 7, whenWasBorn, 1, new Color(0,100,0), "Turtle", coords, world, typeOfOrganism.TURTLE, true);
    }
    
    @Override
    public void action() {
        Random rand = new Random();
        int n = rand.nextInt(100);
        if(n < 25){
            int x = this.getCoords().getX();
            int y = this.getCoords().getY();
            this.setDirections(moveRange);
            direction d = this.randDirection();
            if (d == direction.UP) {
                y -= moveRange;
            }else if (d == direction.DOWN) {
                y += moveRange;
            }else if (d == direction.LEFT) {
                x += moveRange;
            }else if (d == direction.RIGHT) {
                x -= moveRange;
            }
            Coordinates c1 = this.getCoords();
            this.setCoords(x, y);
            world.getBoard().moveOrganism(this, c1);
        }
    }
    
    @Override
    public void specialColision(Organism other, int x, int y, boolean isAttacking) {
	if (other.getType() == type) {
            this.reproduction(this, (Animal)other);
	}else if (other.getStrenght() >= 5) {
            other.getWorld().deleteOrganism(this);
            Coordinates c1 = other.getCoords();
            other.setCoords(x, y);
            world.getBoard().moveOrganism(other, c1);
            world.addComment(other.getOrganismName() + "[x = " + other.getCoords().getX() + "; y = " + other.getCoords().getY() + "] kills " + this.getOrganismName() + "[x = " + this.getCoords().getX() + "; y = " + this.getCoords().getY() + ".\n");
	}else if(other.getIsAnimal()){
            if (isAttacking) {
                Coordinates c = other.getCoords();
		this.setCoords(x, y);
		world.getBoard().moveOrganism(this, c);
		other.setFreeDirections(((Animal)other).getMoveRange());
		direction d = other.randDirection();
		int x1 = other.getCoords().getX(), y1 = other.getCoords().getY();
		if (d == direction.UP) {
                    y1 -= moveRange;
		}else if (d == direction.DOWN) {
                    y1 += moveRange;
		}else if (d == direction.LEFT) {
                    x1 += moveRange;
		}else if (d == direction.RIGHT) {
                    x1 -= moveRange;
		}
		Coordinates c1 = other.getCoords();
		other.setCoords(x1, y1);
		world.getBoard().moveOrganism(other, c1);
                world.addComment(this.getOrganismName() + "[x = " + this.getCoords().getX() + "; y = " + this.getCoords().getY() + "] expels " + other.getOrganismName() + "[x = " + other.getCoords().getX() + "; y = " + other.getCoords().getY() + ".\n");
            }
	}
    }
}
