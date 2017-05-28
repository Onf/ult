
/**
 * Write a description of class board here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 *
 *
 *vars: area, [][] board, winner, finished
 *    drawRow(int r)
 *    getWinner()
 *    move(int tile, String symbol)
 */
public class Board
{
    private int area;
    private Tile[][] board;
    private int winner;
    private boolean finished = false;
    
    public Board(int a) {
        area = a;
        board = new Tile[3][3];
        int count = 1;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                board[i][j] = new Tile(count, " ");
                count++;
            }
        }
    }
    
    public void drawRow(int r) {
        for(int i = 0; i < 3; i++) {
            System.out.print((i == 0) ? "# "+board[r][i]+" |" : (i == 2) ? " "+board[r][i]+" #" : " "+board[r][i]+" |");
        }
    }
    
    public int getWinner() {
        return winner;
    }
    
    public boolean move(int tile, String symbol) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                tile--;
                if(tile == 0) {
                    if(board[i][j].getSymbol().equals(" "))
                        board[i][j].setSymbol(symbol);
                    else
                        return false;
                }
            }
        }
        return true;
    }

    
}
