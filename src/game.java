
/**
 * Write a description of class game here.
 *
 * @author (your name)
 * @version (a version number or a date)
 *
 *
 *
 *drawBoard(Board[][] TTT)
 *findIndexOfSubBoard(int subBoard)
 *drawTutorial1()
 *drawTutorial2()
 */


import java.util.*;
public class game
{
    public static void main(String [] args) {
        Scanner read = new Scanner(System.in);
        Board[][] TTT = new Board[3][3];
        int count = 1;
        int turn = 2;

        System.out.print("Please set the window to fullscreen. Press ENTER to continue... "); read.nextLine();
        drawTutorial1();
        System.out.print("\nPress ENTER to continue... "); read.nextLine();
        drawTutorial2();
        System.out.print("\nPress ENTER to start the game... "); read.nextLine();
        System.out.print("\f");

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                TTT[i][j] = new Board(count);
                count++;
            }
        }

        System.out.println("Player " + (turn%2+1) + "'s turn.");
        drawBoard(TTT);

        int subBoard, tile;
        System.out.print("Choose a sub-board: "); subBoard = read.nextInt();
        while (subBoard < 1 || subBoard > 9) {
            System.out.print("Invalid selection. Choose a sub-board: ");
            subBoard = read.nextInt();
        }
        System.out.print("Choose a tile number: "); tile = read.nextInt();
        while (tile < 1 || tile > 9) {
            System.out.print("Invalid selection. Choose a tile number: ");
            tile = read.nextInt();
        }
        String temp = findIndexOfSubBoard(subBoard);
        int c = Integer.parseInt(temp.substring(1));
        int r = Integer.parseInt(temp.substring(0,1));
        TTT[r][c].move(tile, (turn%2==0)?"X":"O");
        System.out.print("\f");
        drawBoard(TTT);

        while(true) {
            turn++;
            subBoard = tile;
            System.out.print("Now at sub-board " + subBoard + ". Please choose a tile within that sub-board: ");
            tile = read.nextInt();
            temp = findIndexOfSubBoard(subBoard);
            c = Integer.parseInt(temp.substring(1));
            r = Integer.parseInt(temp.substring(0,1));
            while(TTT[r][c].move(tile, (turn%2==0)?"X":"O") == false) {
                System.out.print("Invalid move. Try again: "); tile = read.nextInt();
                temp = findIndexOfSubBoard(subBoard);
                c = Integer.parseInt(temp.substring(1));
                r = Integer.parseInt(temp.substring(0,1));
            }
            System.out.print("\f");

            System.out.println("Player " + (turn%2+1) + "'s turn.");
            drawBoard(TTT);
        }
    }

    //finds index of the board number chosen
    public static String findIndexOfSubBoard(int subBoard) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                subBoard--;
                if(subBoard==0) {
                    return (""+i+j);
                }
            }
        }
        return null;
    }

    public static void drawBoard(Board[][] TTT) {
        System.out.println("#######################################");
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                for(int k = 0; k < 3; k++) {
                    TTT[i][j].drawRow(k);
                }
                System.out.println((j==2)?"":"\n#-----------##-----------##-----------#");
            }
            System.out.println("#######################################");
        }
    }

    public static void drawTutorial1() {
        System.out.println("#######################################\n"+
                "#           ##           ##           #\n"+
                "#           ##           ##           #\n"+
                "#     1     ##     2     ##     3     #\n"+
                "#           ##           ##           #\n"+
                "#           ##           ##           #\n"+
                "#######################################\n"+
                "#           ##           ##           #\n"+
                "#           ##           ##           #\n"+
                "#     4     ##     5     ##     6     #\n"+
                "#           ##           ##           #\n"+
                "#           ##           ##           #\n"+
                "#######################################\n"+
                "#           ##           ##           #\n"+
                "#           ##           ##           #\n"+
                "#     7     ##     8     ##     9     #\n"+
                "#           ##           ##           #\n"+
                "#           ##           ##           #\n"+
                "#######################################\n");
        System.out.println("Each section of the board is assigned a number. Use these numbers to choose a sub-board at the start of the game.");

    }
    public static void drawTutorial2() {
        System.out.println("#######################################\n"+
                "# 1 | 2 | 3 ## 1 | 2 | 3 ## 1 | 2 | 3 #\n"+
                "#-----------##-----------##-----------#\n"+
                "# 4 | 5 | 6 ## 4 | 5 | 6 ## 4 | 5 | 6 #\n"+
                "#-----------##-----------##-----------#\n"+
                "# 7 | 8 | 9 ## 7 | 8 | 9 ## 7 | 8 | 9 #\n"+
                "#######################################\n"+
                "# 1 | 2 | 3 ## 1 | 2 | 3 ## 1 | 2 | 3 #\n"+
                "#-----------##-----------##-----------#\n"+
                "# 4 | 5 | 6 ## 4 | 5 | 6 ## 4 | 5 | 6 #\n"+
                "#-----------##-----------##-----------#\n"+
                "# 7 | 8 | 9 ## 7 | 8 | 9 ## 7 | 8 | 9 #\n"+
                "#######################################\n"+
                "# 1 | 2 | 3 ## 1 | 2 | 3 ## 1 | 2 | 3 #\n"+
                "#-----------##-----------##-----------#\n"+
                "# 4 | 5 | 6 ## 4 | 5 | 6 ## 4 | 5 | 6 #\n"+
                "#-----------##-----------##-----------#\n"+
                "# 7 | 8 | 9 ## 7 | 8 | 9 ## 7 | 8 | 9 #\n"+
                "#######################################\n");
        System.out.println("Each sub-board is split into tiles from 1-9. Players take turns using these numbers to choose a spot to mark during the game." +
                "\nWin 3 sub-boards horizontally, vertically, or diagonally to win the game.");
    }
}
