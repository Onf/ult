
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
    
    //checks for winners on the current board
    public int checkBoard() {
        Scanner check = new Scanner(System.in);
        
        //horizontal check
        for(Tile[] b : board) {
            
            //check.nextLine();
            if(b[0].getSymbol().equals(b[1].getSymbol()) && 
                b[0].getSymbol().equals(b[2].getSymbol()) &&
                (b[0].getSymbol().equals("X") || b[0].getSymbol().equals("O"))) {
                if(b[0].getSymbol().equals("X")) {
                    winner = 1;
                    return winner;
                }
                else if(b[0].getSymbol().equals("O")) {
                    winner = 2;
                    return winner;
                }
            }
        }
        
        //vertical check
        for(int i = 0; i < 3; i++) {
            if(board[0][i].getSymbol().equals(board[1][i].getSymbol()) && 
                board[0][i].getSymbol().equals(board[2][i].getSymbol()) &&
                (board[0][i].getSymbol().equals("X") || board[0][i].equals("O"))) {
                if(board[0][i].getSymbol().equals("X")) {
                    winner = 1;
                    return area;
                }
                else if(board[0][i].getSymbol().equals("O")) {
                    winner = 2;
                    return area;
                }
            }
        }
        
        //diagonal checks
        
        return 0;
    
}
