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
public class Fox extends Animal{
    
    public Fox(int  whenWasBorn, Coordinates coords, World world){
        super(3, 7, whenWasBorn, 1, new Color(255,140,0), "Fox",coords, world, typeOfOrganism.FOX, false);
    }
    
    @Override
    public void action() {
	int x = this.getCoords().getX();
	int y = this.getCoords().getY();
	this.setFreeDirections(moveRange);
	direction d = this.randDirection();
	if (d == direction.UP) {
            y -= moveRange;
	}
	else if (d == direction.DOWN) {
            y += moveRange;
	}
	else if (d == direction.LEFT) {
            x += moveRange;
	}
	else if (d == direction.RIGHT) {
            x -= moveRange;
	}
	Coordinates c1 = this.getCoords();
	this.setCoords(x, y);
	world.getBoard().moveOrganism(this, c1);
    }
    
//    @Override
//    public String getOrganismName() {
//	return "Fox ";
//    }
}
