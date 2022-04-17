package po_projekt2.organisms;

import java.awt.Color;
import java.util.Random;
import po_projekt2.Organism;
import po_projekt2.World;
import po_projekt2.worldElements.Coordinates;

/**
 *
 * @author Bogna
 */
public abstract class Plant extends Organism{
    public Plant(int strenght, int  whenWasBorn, Color color, String name, Coordinates coords, World world, typeOfOrganism type, boolean hasSpecialColision){
        super(strenght, 0, whenWasBorn, color, name, coords, hasSpecialColision, false, world, type);
    }

    public void action(){
        Random rand = new Random();
	int n = rand.nextInt(50);
	if (n >= 45) {
		spread();
	}
    }

    public void colision(Organism attacking, Organism other, int x, int y){}

    public void specialColision(Organism other, int x, int y, boolean isAttacking){}

    protected void spread(){
	this.setFreeDirections(1);
	direction d = randDirection();
	int x = this.getCoords().getX(), y = this.getCoords().getY();
	if (d == direction.UP) {
		y -= 1;
	}else if (d == direction.DOWN) {
		y += 1;
	}else if (d == direction.LEFT) {
		x += 1;
	}else if (d == direction.RIGHT) {
		x -= 1;
	}else {
		return;
	}
	Coordinates newCoordinates = new Coordinates(x, y);
	Organism newOrganism = world.createNewOrganism(type, newCoordinates);
	world.addOrganism(newOrganism);
	world.getBoard().setOrganism(newOrganism);
        world.addComment("New organism was born: " + newOrganism.getOrganismName() + "[ x = " + newOrganism.getCoords().getX() + "; y = " + newOrganism.getCoords().getY() + "].\n");
    }
}
