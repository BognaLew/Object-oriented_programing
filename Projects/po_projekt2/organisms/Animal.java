package po_projekt2.organisms;

import java.awt.Color;
import po_projekt2.Organism;
import po_projekt2.World;
import po_projekt2.organisms.animals.Human;
import po_projekt2.worldElements.Coordinates;

/**
 *
 * @author Bogna
 */
public abstract class Animal extends Organism{
    protected int moveRange;
    
    public Animal(int strenght, int initiative, int  whenWasBorn, int moveRange, Color color, String name, Coordinates coords, World world, typeOfOrganism type, boolean hasSpecialColision){
        super(strenght, initiative, whenWasBorn, color, name, coords, hasSpecialColision, true, world, type);
        this.moveRange = moveRange;
    }
    
    @Override
    public void action(){
	int x = this.getCoords().getX();
	int y = this.getCoords().getY();
	this.setDirections(moveRange);
	direction d = this.randDirection();
	if (d == direction.UP) {
            y -= moveRange;
	}else if (d == direction.DOWN) {
            y += moveRange;
	}else if(d == direction.LEFT){
            x += moveRange;
	}else if (d == direction.RIGHT) {
            x -= moveRange;
	}else {
            return;
	}
	Organism tmp = world.getBoard().getOrganism(x, y);
	if (tmp == null) {
            Coordinates c1 = this.getCoords();
            this.setCoords(x, y);
            world.getBoard().moveOrganism(this, c1);
	}else {
            this.colision(this, tmp, x, y);
	}
    }
    
    @Override
    public void colision(Organism attacking, Organism other, int x, int y){
        if(other.getType() == attacking.getType()){
            this.reproduction((Animal)attacking, (Animal)other);
        }else if(other.getHasSpecialColision()){
            other.specialColision(attacking, x, y, false);
        }else if(attacking.getHasSpecialColision()){
            this.specialColision(other, x, y, true);
        }else if(attacking.getStrenght() >= other.getStrenght()){
            if(other instanceof Human){
                attacking.getWorld().setIsHumanAlive(false);
            }
            attacking.getWorld().deleteOrganism(other);
            Coordinates c1 = this.getCoords();
	    attacking.setCoords(x, y);
            attacking.getWorld().getBoard().moveOrganism(attacking, c1);
            world.addComment(attacking.getOrganismName() + "[x = " + attacking.getCoords().getX() + "; y = " + attacking.getCoords().getY() + "] kills " + other.getOrganismName() + "[x = " + other.getCoords().getX() + "; y = " + other.getCoords().getY() + ".\n");
        }else{
            if (attacking instanceof Human) {
                    other.getWorld().setIsHumanAlive(false);
            }
            other.getWorld().getBoard().removeOrganism(attacking.getCoords());
            other.getWorld().deleteOrganism(attacking);
            world.addComment(other.getOrganismName() + "[x = " + other.getCoords().getX() + "; y = " + other.getCoords().getY() + "] kills " + attacking.getOrganismName() + "[x = " + attacking.getCoords().getX() + "; y = " + attacking.getCoords().getY() + ".\n");
        }
    }
    
    @Override
    public void specialColision(Organism other, int x, int y, boolean isAttacking){}
    
    public int getMoveRange(){
        return this.moveRange;
    }
    
    protected void reproduction(Animal moving, Animal other){
        if (moving.reproduced || other.reproduced) {
            return;
	}else{
            Coordinates newCoords = new Coordinates();
            setFreeDirections(1);
            direction d = randDirection();
            int x = getCoords().getX(), y = getCoords().getY();
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
            newCoords.setX(x);
            newCoords.setY(y);
            Organism newOrganism = world.createNewOrganism(moving.getType(), newCoords);
            moving.getWorld().addOrganism(newOrganism);
            moving.getWorld().getBoard().setOrganism(newOrganism);
            moving.setReproduced(true);
            other.setReproduced(true);
            world.addComment("New organism was born: " + newOrganism.getOrganismName() + "[ x = " + newOrganism.getCoords().getX() + "; y = " + newOrganism.getCoords().getY() + "].\n");
	}
    }
    
}
