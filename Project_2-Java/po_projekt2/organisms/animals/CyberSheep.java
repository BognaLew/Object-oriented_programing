package po_projekt2.organisms.animals;

import java.awt.Color;
import po_projekt2.Organism;
import po_projekt2.World;
import po_projekt2.organisms.Animal;
import po_projekt2.organisms.plants.Hogweed;
import po_projekt2.worldElements.Coordinates;

/**
 *
 * @author Bogna
 */
public class CyberSheep extends Animal{
    private Hogweed targetedHogweed;
    
    public CyberSheep(int  whenWasBorn, Coordinates coords, World world){
        super(11, 4, whenWasBorn, 1, new Color(112,128,144), "Cyber Sheep", coords, world, typeOfOrganism.CYBER_SHEEP, false);
        targetedHogweed = null;
    }
    
    private void actLikeSheep(){
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
    
    private direction choosePath(){
        int x = Math.abs(this.coords.getX() - targetedHogweed.getCoords().getX());
        int y = Math.abs(this.coords.getY() - targetedHogweed.getCoords().getY());
        if (x >= y) {
            if (this.coords.getX() > targetedHogweed.getCoords().getX()) {
                return direction.RIGHT;
            } else {
                return direction.LEFT;
            }
        } else {
            if (this.coords.getY() > targetedHogweed.getCoords().getY()) {
                return direction.UP;
            } else {
                return direction.DOWN;
            }
        }
    }
    
    private direction randDirectionOnPath(){
        if(directions[0] == true || directions[1] == true || directions[2] == true || directions[3] == true){
            direction d;
            while(true){
                d = choosePath();
                if (d == direction.UP && directions[2] == true) {
                    return direction.UP;
                }else if (d == direction.DOWN && directions[3] == true) {
                    return direction.DOWN;
                }else if(d == direction.LEFT && directions[0] == true){
                    return direction.LEFT;
                }else if (d == direction.RIGHT && directions[1] == true) {
                    return direction.RIGHT;
                }
                
            }
        }
        return direction.NONE;
    }
    
    @Override
    public void action(){
        int x = this.getCoords().getX();
	int y = this.getCoords().getY();
        targetedHogweed = searchForHogweed();
        if(targetedHogweed == null){
            actLikeSheep();
        }else{
            setDirections(moveRange);
            direction d = randDirectionOnPath();
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
    }
    
    private Hogweed searchForHogweed(){
        int distance = 0;
        int tmpD = 0;
        Hogweed h = null;
        for(int y = 0; y < world.getBoard().getSizeY(); y++){
            for(int x = 0; x < world.getBoard().getSizeX(); x++){
                Organism o = world.getBoard().getOrganism(x, y);
                if(o != null && o.getType() == typeOfOrganism.HOGWEED){
                    if(distance == 0){
                        distance = Math.abs(this.coords.getX() - o.getCoords().getX()) + Math.abs(this.coords.getY() - o.getCoords().getY());
                    }
                    tmpD = Math.abs(this.coords.getX() - o.getCoords().getX()) + Math.abs(this.coords.getY() - o.getCoords().getY());
                    if(tmpD <= distance){
                        h = (Hogweed)o;
                    }
                }
            }
        }
        return h;
    }
}
