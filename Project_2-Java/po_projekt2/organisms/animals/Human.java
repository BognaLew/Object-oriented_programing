package po_projekt2.organisms.animals;

import java.awt.Color;
import java.util.Random;
import po_projekt2.Organism;
import po_projekt2.World;
import po_projekt2.organisms.Animal;
import po_projekt2.worldElements.Coordinates;

/**
 *
 * @author Bogna
 */
public class Human extends Animal{
    private int numberOfTurnsSkillWillBeActive;
    private boolean skillIsActive, isAlive;
    private direction d;
    
    public Human(Coordinates coords, World world){
        super(5, 4, 0, 1, new Color(255,235,205), "Human",coords, world, typeOfOrganism.HUMAN, false);
        this.numberOfTurnsSkillWillBeActive = 0;
	this.skillIsActive = false;
	this.isAlive = true;
	this.d = direction.NONE;
    }
    
    public boolean getIsAlive() {
	return isAlive;
    }

    public boolean getSkillIsActive() {
	return skillIsActive;
    }

    public int getNumberOfTurnsSkillWillBeActive() {
	return numberOfTurnsSkillWillBeActive;
    }

    public direction getDirection() {
	return d;
    }

    public void setSkillIsActive(boolean isActive) {
	this.skillIsActive = isActive;
    }

    public void setIsAlive(boolean isAlive) {
	this.isAlive = isAlive;
    }

    public void setNumberOfTurnsSkillWillBeActive(int number) {
	this.numberOfTurnsSkillWillBeActive = number;
    }

    public void setMoveRange(int moveRange){
	this.moveRange = moveRange;
    }

    public void setDirection(direction d) {
	this.d = d;
    }

    public void activateSkill() {
	this.setMoveRange(2);
	this.setNumberOfTurnsSkillWillBeActive(10);
	this.setSkillIsActive(true); 
        world.clearComment();
        world.addComment("Skill activated.\n");
    }

    public void deactivateSkill() {
	this.setMoveRange(1);
	this.setNumberOfTurnsSkillWillBeActive(5);
	this.setSkillIsActive(false);
        world.addComment("Skill deactivated.\n");
    }
    
    @Override
    public void action() {
	if (skillIsActive) {
		if (numberOfTurnsSkillWillBeActive < 7) {
                    Random rand = new Random();
			int i = rand.nextInt(100);;
			if (i >= 50) {
				setMoveRange(2);
			}else {
				setMoveRange(1);
			}
		}
		numberOfTurnsSkillWillBeActive--;
		if (numberOfTurnsSkillWillBeActive == 5) {
			deactivateSkill();
		}
	}else if (numberOfTurnsSkillWillBeActive > 0) {
		numberOfTurnsSkillWillBeActive--;
	}
	if (d != direction.NONE) {
            Coordinates c = new Coordinates();
            int x = this.getCoords().getX();
            int y = this.getCoords().getY();
            if (d == direction.UP && y - moveRange >= 0) {
                c.setX(x);
                c.setY(y - moveRange);
                y -= moveRange;
            }else if (d == direction.DOWN && y + moveRange < world.getBoard().getSizeY()) {
                c.setX(x);
                c.setY(y + moveRange);
                y += moveRange;
            }else if (d == direction.LEFT && x - moveRange >= 0) {
                c.setX(x - moveRange);
                c.setY(y);
                x -= moveRange;
            }else if (d == direction.RIGHT && x + moveRange < world.getBoard().getSizeX()) {
                c.setX(x + moveRange);
                c.setY(y);
                x += moveRange;
            }
            Organism tmp = world.getBoard().getOrganism(c.getX(), c.getY());
            if (tmp == null) {
                Coordinates c1 = this.getCoords();
                this.setCoords(x, y);
                world.getBoard().moveOrganism(this, c1);
            }else {
                this.colision(this, tmp, x, y);
            }
	}
    }
}
