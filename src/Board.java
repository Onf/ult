
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
public class Board {
    private int area;
    private Tile[][] board;
    private int winner;

    public Board(int a) {
        area = a;
        board = new Tile[3][3];
        int count = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = new Tile(count, " ");
                count++;
            }
        }
    }

    public void drawRow(int r, int subBoard, String temp, String highlighter, String normal) {
        
        for (int i = 0; i < 3; i++) {
            if(subBoard == area) {
                System.out.print((i == 0) ? highlighter+" "+board[r][i]+" |" :
                                (i == 2) ? " "+board[r][i]+" "+highlighter :
                                " " + board[r][i] + " |");
            }
            else {
                System.out.print((i == 0) ? normal+" "+board[r][i]+" |" :
                                    (i == 2) ? " "+board[r][i]+" "+normal :
                                    " " + board[r][i] + " |");
            }
        }
        
    }

    public int getWinner() {
        return winner;
    }

    public boolean move(int tile, String symbol) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tile--;
                if (tile == 0) {
                    if (board[i][j].getSymbol().equals(" "))
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
        //Scanner check = new Scanner(System.in);

        //horizontal check
        for (Tile[] b : board) {
            //check.nextLine();
            if (b[0].getSymbol().equals(b[1].getSymbol()) &&
                    b[0].getSymbol().equals(b[2].getSymbol()) &&
                    (b[0].getSymbol().equals("X") || b[0].getSymbol().equals("O"))) {
                if (b[0].getSymbol().equals("X")) {
                    winner = 1;
                    return area;
                } else if (b[0].getSymbol().equals("O")) {
                    winner = 2;
                    return area;
                }
            }
        }

        //vertical check
        for (int i = 0; i < 3; i++) {
            if (board[0][i].getSymbol().equals(board[1][i].getSymbol()) &&
                    board[0][i].getSymbol().equals(board[2][i].getSymbol()) &&
                    (board[0][i].getSymbol().equals("X") || board[0][i].getSymbol().equals("O"))) {
                if (board[0][i].getSymbol().equals("X")) {
                    winner = 1;
                    return area;
                } else if (board[0][i].getSymbol().equals("O")) {
                    winner = 2;
                    return area;
                }
            }
        }

        //diagonal checks
        if (board[0][0].getSymbol().equals(board[1][1].getSymbol()) && board[0][0].getSymbol().equals(board[2][2].getSymbol())) {
            if (board[0][0].getSymbol().equals("X")) {
                winner = 1;
                return area;
            } else if (board[0][0].getSymbol().equals("O")) {
                winner = 2;
                return area;
            }
        }
        if (board[0][2].getSymbol().equals(board[1][1].getSymbol()) && board[0][2].getSymbol().equals(board[2][0].getSymbol())) {
            if (board[0][2].getSymbol().equals("X")) {
                winner = 1;
                return area;
            } else if (board[0][2].getSymbol().equals("O")) {
                winner = 2;
                return area;
            }
        }

        //filled check
        for (Tile[] t : board) {
            for (Tile tile : t) {
                if (tile.getSymbol().equals(" ")) {
                    winner = 0;
                    return 0;
                }
            }
        }

        return 10;
    }
}
