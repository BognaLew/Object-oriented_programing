package po_projekt2;

import po_projekt2.worldElements.Coordinates;

import java.util.Random;
import java.awt.Color;
/**
 *
 * @author Bogna
 */


public abstract class Organism {
    public enum direction{
        NONE,
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
    public enum typeOfOrganism{
        HUMAN,		//człowiek
	WOLF,		//wilk
	SHEEP,		//owca
	FOX,		//lis
	TURTLE,		//żółw
	ANTELOPE,	//antylopa
	GRASS,		//trawa
	MILT,		//mlecz
	GUARANA,	//guarana
	BELLADONNA,	//wilcze jagody
	HOGWEED,	//barsz Sosnowskiego
        CYBER_SHEEP
    }
    protected String name;
    protected int strenght, initiative, whenWasBorn;
    protected Color color;
    protected boolean reproduced, hasSpecialColision, isAnimal;
    protected boolean[] directions = new boolean[4];
    protected typeOfOrganism type;
    protected Coordinates coords;
    protected World world;
    
    protected Organism(int strenght, int initiative, int whenWasBorn, Color color, String name, Coordinates coords, boolean hasSpecialColision, boolean isAnimal, World world, typeOfOrganism type){
        this.strenght = strenght;
        this.initiative = initiative;
        this.whenWasBorn = whenWasBorn;
        this.color = color;
        this.name = name;
        this.reproduced = false;
        this.hasSpecialColision = hasSpecialColision;
        this.isAnimal = isAnimal;
        this.type = type;
        this.coords = coords;
        this.world = world;
    }
    
    
    public abstract void action();
    
    public abstract void colision(Organism attacking, Organism other, int x, int y);
    
    public abstract void specialColision(Organism other, int x, int y, boolean isAttacking);
    
    public String getOrganismName(){
        return name;
    };
    
    
    public void setStrenght(int strenght){
        this.strenght = strenght;
    }
    
    public void setWhenWasBorn(int whenWasBorn){
        this.whenWasBorn = whenWasBorn;
    }
    
    public void setDirections(int moveRange){
        directions[0] = true;
	directions[1] = true;
	directions[2] = true;
	directions[3] = true;
	Coordinates left = new Coordinates(coords.getX() + moveRange, coords.getY());
        Coordinates right = new Coordinates(coords.getX() - moveRange, coords.getY());
        Coordinates up = new Coordinates(coords.getX(), coords.getY()- moveRange);
        Coordinates down = new Coordinates(coords.getX(), coords.getY() + moveRange);
	if (left.getX() >= world.getBoard().getSizeX()) {
		directions[0] = false;
	}
	if (right.getX() < 0 ) {
		directions[1] = false;
	}
	if (up.getY() < 0) {
		directions[2] = false;
	}
	if (down.getY() >= world.getBoard().getSizeY()) {
		directions[3] = false;
	}
    }
    
    public void setFreeDirections(int moveRange){
        directions[0] = true;
	directions[1] = true;
	directions[2] = true;
	directions[3] = true;
	Coordinates left = new Coordinates(coords.getX() + moveRange, coords.getY());
        Coordinates right = new Coordinates(coords.getX() - moveRange, coords.getY());
        Coordinates up = new Coordinates(coords.getX(), coords.getY()- moveRange);
        Coordinates down = new Coordinates(coords.getX(), coords.getY() + moveRange);
	if (left.getX() >= world.getBoard().getSizeX()) {
		directions[0] = false;
	}else if (world.getBoard().getOrganism(left.getX(), left.getY()) != null && (!this.getIsAnimal() || world.getBoard().getOrganism(left.getX(), left.getY()).getStrenght() > this.strenght)) {
		directions[0] = false;
	}
	if (right.getX() < 0 ) {
		directions[1] = false;
	}else if (world.getBoard().getOrganism(right.getX(), right.getY()) != null && (!this.getIsAnimal() || world.getBoard().getOrganism(right.getX(), right.getY()).getStrenght() > this.strenght)) {
		directions[1] = false;
	}
	if (up.getY() < 0) {
		directions[2] = false;
	}else if (world.getBoard().getOrganism(up.getX(), up.getY()) != null && (!this.getIsAnimal() || world.getBoard().getOrganism(up.getX(), up.getY()).getStrenght() > this.strenght)) {
		directions[2] = false;
	}
	if (down.getY() >= world.getBoard().getSizeY()) {
		directions[3] = false;
	}else if (world.getBoard().getOrganism(down.getX(), down.getY()) != null && (!this.getIsAnimal() || world.getBoard().getOrganism(down.getX(), down.getY()).getStrenght() > this.strenght)) {
		directions[3] = false;
	}
    }
    
    public void setCoords(int x, int y){
        coords.setX(x);
        coords.setY(y);
    }
    
    public void setReproduced(boolean reproduced){
        this.reproduced = reproduced;
    }
    
    
    public int getStrenght(){
        return this.strenght;
    }
    
    public int getInitiative(){
        return this.initiative;
    }
    
    public int getWhenWasBorn(){
        return this.whenWasBorn;
    }
    
    public Coordinates getCoords(){
        Coordinates c = new Coordinates(this.coords.getX(), this.coords.getY());
        return c;
    }
    
    public Color getColor(){
        return this.color;
    }
    
    public boolean getReproduced(){
        return this.reproduced;
    }
    
    public boolean getIsAnimal(){
        return this.isAnimal;
    }
    
    public boolean getHasSpecialColision(){
        return this.hasSpecialColision;
    }
    
    
    public typeOfOrganism getType(){
        return this.type;
    }
    
    public direction randDirection(){
        Random rand = new Random();
        if(directions[0] == false && directions[1] == false && directions[2] == false && directions[3] == false){
            return direction.NONE;
        }else{
            while(true){
                int d = rand.nextInt(4);
                if(directions[d] == true){
                    if(d == 0){
                        return direction.LEFT;
                    }else if(d == 1){
                        return direction.RIGHT;
                    }else if(d == 2){
                        return direction.UP;
                    }else if(d == 3){
                        return direction.DOWN;
                    }
                }
            }
        }
    }
    
    public typeOfOrganism randType(){
        Random rand = new Random();
        int t = rand.nextInt(11);
        switch(t){
            case 0: return typeOfOrganism.WOLF;
            case 1: return typeOfOrganism.SHEEP;
            case 2: return typeOfOrganism.FOX;
            case 3: return typeOfOrganism.TURTLE;
            case 4: return typeOfOrganism.ANTELOPE;
            case 5: return typeOfOrganism.GRASS;
            case 6: return typeOfOrganism.MILT;
            case 7: return typeOfOrganism.GUARANA;
            case 8: return typeOfOrganism.BELLADONNA;
            case 9: return typeOfOrganism.HOGWEED;
            case 10: return typeOfOrganism.CYBER_SHEEP;    
        }
        return typeOfOrganism.SHEEP;
    }
    
    public World getWorld(){
        return this.world;
    }
}
