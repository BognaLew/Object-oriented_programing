
package po_projekt2.worldElements;

/**
 *
 * @author Bogna
 */
public class Coordinates {
    private int x, y;
    
    public Coordinates(){
        this.x = 0;
        this.y = 0;
    }
    
    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }
}
