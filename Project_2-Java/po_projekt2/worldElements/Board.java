package po_projekt2.worldElements;

import java.util.Random;
import po_projekt2.Organism;
import po_projekt2.worldElements.Coordinates;

/**
 *
 * @author Bogna
 */
public class Board {
    private int sizeX, sizeY;
    private Organism[][] board;
    
    public Board(int sizeX, int sizeY){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        
        board = new Organism[sizeY][sizeX];
        for(int i = 0; i < sizeY; i++){
            for(int j = 0; j < sizeX; j++){
                board[i][j] = null;
            }
        }
    }
    
    public Organism getOrganism(int x, int y){
        return board[y][x];
    }
    
    public void setOrganism(Organism o){
        this.board[o.getCoords().getY()][o.getCoords().getX()] = o;
    }
    
    public void removeOrganism(Coordinates coords){
        board[coords.getY()][coords.getX()] = null;
    }
    
    public void moveOrganism(Organism o, Coordinates prevCoords){
        this.removeOrganism(prevCoords);
        this.setOrganism(o);
    }
    
    public int getSizeX(){
        return this.sizeX;
    }
    
    public int getSizeY(){
        return this.sizeY;
    }
    
    public Coordinates randEmptyArea(){
        Random rand = new Random();
        while(true){
            int x = rand.nextInt(sizeX);
            int y = rand.nextInt(sizeY);
            if(board[y][x] == null){
                Coordinates coords = new Coordinates(x,y);
                return coords;
            }
        }
    }
}