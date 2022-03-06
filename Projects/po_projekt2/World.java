/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package po_projekt2;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import po_projekt2.worldElements.Board;
import po_projekt2.Organism;
import po_projekt2.worldElements.WorldDrafter;

import po_projekt2.Organism.typeOfOrganism;
import po_projekt2.organisms.animals.Antelope;
import po_projekt2.organisms.animals.CyberSheep;
import po_projekt2.organisms.animals.Fox;
import po_projekt2.organisms.animals.Human;
import po_projekt2.organisms.animals.Sheep;
import po_projekt2.organisms.animals.Turtle;
import po_projekt2.organisms.animals.Wolf;
import po_projekt2.organisms.plants.Belladonna;
import po_projekt2.organisms.plants.Grass;
import po_projekt2.organisms.plants.Guarana;
import po_projekt2.organisms.plants.Hogweed;
import po_projekt2.organisms.plants.Milt;

import po_projekt2.worldElements.Coordinates;

/**
 *
 * @author Bogna
 */
public class World {
    private Board board;
    private Narrator narrator;
    private int numberOfTurn, numberOfOrganisms;
    private boolean isHumanAlive, isEndOfGame, isEndOfTurn;
    private ArrayList<Organism> organisms;
    private WorldDrafter worldDrafter;
    private Human human;
    
    private void sortOrganisms() {
        Collections.sort(organisms, new Comparator<Organism>() {
            @Override
            public int compare(Organism o1, Organism o2) {
                if (o1.getInitiative() != o2.getInitiative())
                    return Integer.valueOf(o2.getInitiative()).compareTo(o1.getInitiative());
                else
                    return Integer.valueOf(o1.getWhenWasBorn()).compareTo(o2.getWhenWasBorn());
            }
        });
    }
    
    public World(int numberOfOrganisms, int sizeX, int sizeY, WorldDrafter worldDrafter){
        this.numberOfTurn = 0;
        this.isHumanAlive = true;
        this.isEndOfGame = false;
        
        organisms = new ArrayList<>();
        board = new Board(sizeX, sizeY);
        this.worldDrafter = worldDrafter;
        
        Coordinates c = board.randEmptyArea();
	Organism tmp = new Human(c, this);
	addOrganism(tmp);
        this.human = (Human)tmp;
        
        for(int i = 1; i < numberOfOrganisms; i++){
            Coordinates coords = board.randEmptyArea();
            Organism.typeOfOrganism type = human.randType();
            Organism o = createNewOrganism(type, coords);
            this.addOrganism(o);
        }
        
        this.isEndOfTurn = true;
    }
    
    public World(WorldDrafter worldDrafter) {
        this.numberOfTurn = 0;
        this.isHumanAlive = true;
        this.isEndOfGame = false;
        
        organisms = new ArrayList<>();
        this.worldDrafter = worldDrafter;
        this.isEndOfTurn = true;
    }
    
    
    private static class Narrator{
        private static String comments;
        
        public Narrator(){
            comments = "";
        }
        
        public static void addComment(String comment){
            comments += comment;
        }
        
        public static String getComments(){
            return comments;
        }
        
        public static void clearComments(){
            comments = "";
        }
    }
    
    
    public boolean saveWorld(String fileName){
        try{
            File file = new File(fileName);
            file.createNewFile();
            
            PrintWriter pw = new PrintWriter(file);
            pw.write(board.getSizeX() + " " + board.getSizeY() + " " + organisms.size() + " " + numberOfTurn + " " + isHumanAlive + "\n");
            for (int i = 0; i < organisms.size(); i++) {
		pw.write(organisms.get(i).getType() + " " + organisms.get(i).getStrenght() + " " + organisms.get(i).getInitiative() + " " + organisms.get(i).getWhenWasBorn() + " " + organisms.get(i).getCoords().getX() + " " + organisms.get(i).getCoords().getY());
		if (organisms.get(i) instanceof Human) {
                    Human tmp = (Human)organisms.get(i);
                    pw.write(" " + tmp.getSkillIsActive() + " " + tmp.getNumberOfTurnsSkillWillBeActive());
		}
		pw.write("\n");
            }
            pw.close();
            return true;
        } catch (IOException e) {
            System.out.println("Error: " + e);
            return false;
        }
    }
    
    
    public boolean loadWorld(String fileName){
        try{
            File file = new File(fileName);

            Scanner scan = new Scanner(file);
            String line = scan.nextLine();
            String[] elements = line.split(" ");
            int sizeX = Integer.parseInt(elements[0]);
            int sizeY = Integer.parseInt(elements[1]);
            
            board = new Board(sizeX, sizeY);
            this.numberOfOrganisms = Integer.parseInt(elements[2]);
            this.numberOfTurn = Integer.parseInt(elements[3]);
            this.isHumanAlive = Boolean.parseBoolean(elements[4]);
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                elements = line.split(" ");
                Organism.typeOfOrganism type = Organism.typeOfOrganism.valueOf(elements[0]);
                int x = Integer.parseInt(elements[4]);
                int y = Integer.parseInt(elements[5]);
                Coordinates coords = new Coordinates(x, y);
                Organism o = null;
                int strenght = Integer.parseInt(elements[1]);
                int whenWasBorn = Integer.parseInt(elements[3]);
                
                if(type == Organism.typeOfOrganism.HUMAN){
                    o = new Human(coords, this);
                    o.setStrenght(strenght);
                    human = (Human)o;
                    boolean skillIsActive = Boolean.parseBoolean(elements[4]);
                    int numberOfTurnWillBeActive = Integer.parseInt(elements[3]);
                    human.setSkillIsActive(skillIsActive);
                    human.setNumberOfTurnsSkillWillBeActive(numberOfTurnWillBeActive);
                }else{
                    o = createNewOrganism(type, coords);
                    o.setStrenght(strenght);
                    o.setWhenWasBorn(whenWasBorn);
                }
                this.addOrganism(o);
            }
            return true;
        }catch (
                IOException e) {
            System.out.println("Error: " + e);
            return false;
        }
    }
    
    
    public void makeTurn() {
        narrator.clearComments();
	if (isEndOfGame) {
            return;
	}
        this.sortOrganisms();
	for (int i = 0; i < organisms.size(); i++) {
            if(organisms.get(i).getWhenWasBorn() != numberOfTurn){
                organisms.get(i).action();
            }
	}
	numberOfTurn++;
    }
    
    public void addComment(String comment){
        narrator.addComment(comment);
    }
    
    public void clearComment(){
        narrator.clearComments();
    }
    
    public Board getBoard(){
        return this.board;
    }
    
    public String getComments(){
        return narrator.getComments();
    }
    
    public WorldDrafter getWorldDrafter(){
        return this.worldDrafter;
    }
    
    public int getNumberOfTurn(){
        return this.numberOfTurn;
    }
    
    public int getNumberOfAnimals(){
        return this.numberOfOrganisms;
    }
    
    public boolean getIsHumanAlive(){
        return this.isHumanAlive;
    }
    
    public boolean getIsEndOfGame(){
        return this.isEndOfGame;
    }
    
    public boolean getIsEndOfTurn(){
        return this.isEndOfTurn;
    }
    
    public Human getHuman(){
        return this.human;
    }
    
    public void setIsHumanAlive(boolean isHumanAlive){
        this.isHumanAlive = isHumanAlive;
    }
    
    public void setIsEndOfGame(boolean isEndOfGame){
        this.isHumanAlive = isHumanAlive;
    }
    
    public void setIsEndOfTurn(boolean isEndOfTurn){
        this.isEndOfTurn = isEndOfTurn;
    }
    
    public void addOrganism(Organism newOrganism){
        organisms.add(newOrganism);
        this.numberOfOrganisms = organisms.size();
        board.setOrganism(newOrganism);
    }
    
    public Organism createNewOrganism(typeOfOrganism type, Coordinates newCoords){
        Organism newOrganism;
        if(type == Organism.typeOfOrganism.WOLF){
            newOrganism = new Wolf(this.getNumberOfTurn(), newCoords, this);
            return newOrganism;
        } else if(type == Organism.typeOfOrganism.SHEEP){
            newOrganism = new Sheep(this.getNumberOfTurn(), newCoords, this);
            return newOrganism;
        } else if(type == Organism.typeOfOrganism.FOX){
            newOrganism = new Fox(this.getNumberOfTurn(), newCoords, this);
            return newOrganism;
        } else if(type == Organism.typeOfOrganism.TURTLE){
            newOrganism = new Turtle(this.getNumberOfTurn(), newCoords, this);
            return newOrganism;
        } else if(type == Organism.typeOfOrganism.ANTELOPE){
            newOrganism = new Antelope(this.getNumberOfTurn(), newCoords, this);
            return newOrganism;
        } else if(type == Organism.typeOfOrganism.CYBER_SHEEP){
            newOrganism = new CyberSheep(this.getNumberOfTurn(), newCoords, this);
            return newOrganism;
        } else if(type == Organism.typeOfOrganism.GRASS){
            newOrganism = new Grass(this.getNumberOfTurn(), newCoords, this);
            return newOrganism;
        } else if(type == Organism.typeOfOrganism.MILT){
            newOrganism = new Milt(this.getNumberOfTurn(), newCoords, this);
            return newOrganism;
        } else if(type == Organism.typeOfOrganism.GUARANA){
            newOrganism = new Guarana(this.getNumberOfTurn(), newCoords, this);
            return newOrganism;
        } else if(type == Organism.typeOfOrganism.BELLADONNA){
            newOrganism = new Belladonna(this.getNumberOfTurn(), newCoords, this);
            return newOrganism;
        } else if(type == Organism.typeOfOrganism.HOGWEED){
            newOrganism = new Hogweed(this.getNumberOfTurn(), newCoords, this);
            return newOrganism;
        }
        return null;
    }
    
    public void deleteOrganism(Organism o){
        int i = 0;
        while(o != organisms.get(i)){
            i++;
        }
        organisms.remove(i);
        this.numberOfOrganisms = organisms.size();
    }
    
    
}
