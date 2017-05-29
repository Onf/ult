
/**
 * Write a description of class Tile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tile
{
    private int section;
    private String symbol;
    
    public Tile(int s, String sym) {
        section = s;
        symbol = sym;
    }
    
    public int getSection() {
        return section;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public void setSymbol(String s) {
        symbol = s;
    }
}
