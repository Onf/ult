
/**
 * Write a description of class game here.
 * 
 * @author Jabin Gong, Tariq Kariapper, Ali Ahmed
 * @version 
 *
 *
 *
 *drawBoard(Board[][] TTT)
 *findIndexOfSubBoard(int subBoard)
 *drawIntro()
 *drawTutorial1()
 *drawTutorial2()
 *
 *
 *
 *vars: int count, turn, subBoard, tile, c, r, rowNum, xWins, oWins
 *      Board[][] TTT
 *      Scanner read
 *      String temp
 */


import java.util.*;
public class game
{
    public static void main(String [] args) {
        Scanner read = new Scanner(System.in);
        Board[][] TTT = new Board[3][3];
        ArrayList<Integer> won = new ArrayList<Integer>();
        int count = 1;
        int turn = 2;
        ArrayList<Integer> xWins = new ArrayList<Integer>();
        ArrayList<Integer> oWins = new ArrayList<Integer>();
        ArrayList<Integer> tied = new ArrayList<Integer>();

        System.out.print("Please set the window to fullscreen. Press ENTER to continue... "); read.nextLine();
        drawIntro();
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

        drawBoard(TTT, xWins, oWins, tied);
        String symb =  ((turn%2+1)==1)?"(X)":"(O)";
        System.out.println("Player " + (turn%2+1) + "'s turn." + symb);

        int subBoard, tile;
        System.out.print("Choose a sub-board: "); subBoard = read.nextInt();
        while (subBoard < 1 || subBoard > 9 || searchWon(subBoard, won)) {
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
        drawBoard(TTT, xWins, oWins, tied);

        while(checkWinner(TTT) == 0 && checkWinner(TTT) != 3) { //not sure if checkWinner(TTT) != 3 works for tie game condition
            turn++;
            System.out.println("Player " + (turn%2+1) + "'s turn." + ((turn%2+1==1)?"(X)":"(O)"));
            
            if(searchWon(tile, won)) {
                System.out.print("Previous player chose a sub-board that is already won. Choose any other sub-board: ");
                tile = read.nextInt();
                while((tile < 1 || tile > 9) || searchWon(tile, won)) {
                    System.out.print("Invalid move. Try again: "); tile = read.nextInt();   
                }
            }
            subBoard = tile;
            System.out.print("Now at sub-board " + subBoard + ". Please choose a tile within that sub-board: ");
            tile = read.nextInt();
            
            while (tile < 1 || tile > 9) {
                System.out.print("Invalid move. Try again: ");
                tile = read.nextInt();
            }
            
            temp = findIndexOfSubBoard(subBoard);
            c = Integer.parseInt(temp.substring(1));
            r = Integer.parseInt(temp.substring(0,1));
            while(!TTT[r][c].move(tile, (turn % 2 == 0) ? "X" : "O")) {
                System.out.print("Invalid move. Try again: "); tile = read.nextInt();
                while (tile < 1 || tile > 9) {
                    System.out.print("Invalid move. Try again: ");
                    tile = read.nextInt();
                }
                temp = findIndexOfSubBoard(subBoard);
                c = Integer.parseInt(temp.substring(1));
                r = Integer.parseInt(temp.substring(0,1));
            }
            
            won.add(TTT[r][c].checkBoard());
            
            
            if(won.get(won.size()-1) != 0) {
                if(won.get(won.size()-1) == 10) {
                    System.out.println("Sub-board " + subBoard + " has ended in a tie!");
                    won.add(subBoard);
                    tied.add(subBoard);
                }
                else if(turn%2+1 == 1) {
                    xWins.add(subBoard);
                    System.out.println("Player 1 has won sub-board " + subBoard);
                }
                else {
                    oWins.add(subBoard);
                    System.out.println("Player 2 has won sub-board " + subBoard);
                }
            }
            
            System.out.print("\f");
            
            drawBoard(TTT, xWins, oWins, tied);
            
            if(won.get(won.size()-1) != 0) {
                if(won.get(won.size()-1) == 10) {
                    System.out.println("Sub-board " + subBoard + " has ended in a tie!");
                }
                else if(turn%2+1 == 1) {
                    System.out.println("Player 1 has won sub-board " + subBoard);
                }
                else {
                    System.out.println("Player 2 has won sub-board " + subBoard);
                }
            }
        }
        System.out.println("GAME OVER! " + ((checkWinner(TTT)==3?"The game ends in a tie!":
                            "Player " + (checkWinner(TTT)==1?1:2) + " wins!")));
    }

    //checks if the board is won, in which case it would be contained in the ArrayList won
    public static boolean searchWon(int s, ArrayList<Integer> won) {
        return won.contains(s);   
    }
    
    //checks for winners on the board *****************TO BE REVIEWED*****************
    private static int checkWinner(Board[][] TTT) {
        //horizontal check
        for(Board[] b : TTT) {
            if(b[0].getWinner()==(b[1].getWinner()) &&
                b[0].getWinner()==(b[2].getWinner()) &&
                (b[0].getWinner() == 1 || b[0].getWinner() == 2)) {
                    return b[0].getWinner();
                }
        }
        
        //vertical check
        for(int i = 0; i < 3; i++) {
            if(TTT[0][i].getWinner()==(TTT[1][i].getWinner()) && 
                TTT[0][i].getWinner()==(TTT[2][i].getWinner()) &&
                (TTT[0][i].getWinner()==1 || TTT[0][i].getWinner()==2)) {
                return TTT[0][i].getWinner();
            }
        }
        
        //diagonal checks
        if (TTT[0][0].getWinner()==(TTT[1][1].getWinner()) && TTT[0][0].getWinner()==(TTT[2][2].getWinner())){
            if (TTT[0][0].getWinner()==1) {
                return 1;
            } else if (TTT[0][0].getWinner()==2) {
                return 2;
            }
        }
        if (TTT[0][2].getWinner()==(TTT[1][1].getWinner()) && TTT[0][2].getWinner()==(TTT[2][0].getWinner())){
            if (TTT[0][2].getWinner()==1) {
                return 1;
            } else if (TTT[0][2].getWinner()==2) {
                return 2;
            }
        }
        
        //filled check
        for(Board[] b : TTT) {
            for(Board board : b) {
                if(board.getWinner() == 0) {
                    return 0;
                }
            }
        }
        
        return 3;
    }

    //finds index of the board number chosen
    private static String findIndexOfSubBoard(int subBoard) {
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

    
//draws the board one row at a time using the drawRow method of each Board object within TTT
    private static void drawBoard(Board[][] TTT, ArrayList<Integer> x, ArrayList<Integer> o, ArrayList<Integer> t) {
        int rowNum = 0;
        System.out.println("#######################################");
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                for(int k = 0; k < 3; k++) {
                    TTT[i][k].drawRow(rowNum % 3);
                }
                System.out.print((rowNum==1)?"\t\tPlayer 1 won boards: " + x:(rowNum==2)?"\t\tPlayer 2 won boards: " + o:
                                    (rowNum==3)?"\t\tTied boards: " + t:"");
                rowNum++;
                System.out.println((j==2)?"":"\n#-----------##-----------##-----------#");
            }
            System.out.println("#######################################");
        }
    }

    private static void drawIntro() {
        System.out.println("\n" +
                " /##   /## /##   /##     /##                           /##                                \n" +
                "| ##  | ##| ##  | ##    |__/                          | ##                                \n" +
                "| ##  | ##| ## /######   /## /######/####   /######  /######    /######                   \n" +
                "| ##  | ##| ##|_  ##_/  | ##| ##_  ##_  ## |____  ##|_  ##_/   /##__  ##                  \n" +
                "| ##  | ##| ##  | ##    | ##| ## \\ ## \\ ##  /#######  | ##    | ########                  \n" +
                "| ##  | ##| ##  | ## /##| ##| ## | ## | ## /##__  ##  | ## /##| ##_____/                  \n" +
                "|  ######/| ##  |  ####/| ##| ## | ## | ##|  #######  |  ####/|  #######                  \n" +
                " \\______/ |__/   \\___/  |__/|__/ |__/ |__/ \\_______/   \\___/   \\_______/                  \n" +
                "                                                                                          \n" +
                "                                                                                          \n" +
                "                                                                                          \n" +
                " /######## /##                 /########                        /########                 \n" +
                "|__  ##__/|__/                |__  ##__/                       |__  ##__/                 \n" +
                "   | ##    /##  /#######         | ##  /######   /#######         | ##  /######   /###### \n" +
                "   | ##   | ## /##_____/         | ## |____  ## /##_____/         | ## /##__  ## /##__  ##\n" +
                "   | ##   | ##| ##               | ##  /#######| ##               | ##| ##  \\ ##| ########\n" +
                "   | ##   | ##| ##               | ## /##__  ##| ##               | ##| ##  | ##| ##_____/\n" +
                "   | ##   | ##|  #######         | ##|  #######|  #######         | ##|  ######/|  #######\n" +
                "   |__/   |__/ \\_______/         |__/ \\_______/ \\_______/         |__/ \\______/  \\_______/\n" +
                "                                                                                          \n" +
                "                                                                                          \n" +
                "                                                                                          \n" +
                " ");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\n" +
                "                                          __                      __        __                 \n" +
                "                                         |  \\                    |  \\      |  \\                \n" +
                "  _______   ______    ______    ______  _| ##_     ______    ____| ##      | ##____   __    __ \n" +
                " /       \\ /      \\  /      \\  |      \\|   ## \\   /      \\  /      ##      | ##    \\ |  \\  |  \\\n" +
                "|  #######|  ######\\|  ######\\  \\######\\\\######  |  ######\\|  #######      | #######\\| ##  | ##\n" +
                "| ##      | ##   \\##| ##    ## /      ## | ## __ | ##    ##| ##  | ##      | ##  | ##| ##  | ##\n" +
                "| ##_____ | ##      | ########|  ####### | ##|  \\| ########| ##__| ##      | ##__/ ##| ##__/ ##\n" +
                " \\##     \\| ##       \\##     \\ \\##    ##  \\##  ## \\##     \\ \\##    ##      | ##    ## \\##    ##\n" +
                "  \\####### \\##        \\#######  \\#######   \\####   \\#######  \\#######       \\#######  _\\#######\n" +
                "                                                                                     |  \\__| ##\n" +
                "                                                                                      \\##    ##\n" +
                "                                                                                       \\###### \n");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("\n" +
                "    _____            __        __                 \n" +
                "   |     \\          |  \\      |  \\                \n" +
                "    \\#####  ______  | ##____   \\## _______        \n" +
                "      | ## |      \\ | ##    \\ |  \\|       \\       \n" +
                " __   | ##  \\######\\| #######\\| ##| #######\\      \n" +
                "|  \\  | ## /      ##| ##  | ##| ##| ##  | ##      \n" +
                "| ##__| ##|  #######| ##__/ ##| ##| ##  | ##      \n" +
                " \\##    ## \\##    ##| ##    ##| ##| ##  | ##      \n" +
                "  \\######   \\####### \\#######  \\## \\##   \\##      \n" +
                "                                                  \n" +
                "                                                  \n" +
                "                                                  \n");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print(
                "\n" +
                        " ________                    __           \n" +
                        "|        \\                  |  \\          \n" +
                        " \\########______    ______   \\##  ______  \n" +
                        "   | ##  |      \\  /      \\ |  \\ /      \\ \n" +
                        "   | ##   \\######\\|  ######\\| ##|  ######\\\n" +
                        "   | ##  /      ##| ##   \\##| ##| ##  | ##\n" +
                        "   | ## |  #######| ##      | ##| ##__| ##\n" +
                        "   | ##  \\##    ##| ##      | ## \\##    ##\n" +
                        "    \\##   \\####### \\##       \\##  \\#######\n" +
                        "                                      | ##\n" +
                        "                                      | ##\n" +
                        "                                       \\##\n" +
                        "\n");
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("\n" +
                "                           __                  __        __                                                __                                  __           \n" +
                "                          |  \\                |  \\      |  \\                                              |  \\                                |  \\          \n" +
                "  ______   _______    ____| ##       __    __ | ##____  | ##____         ______ ____    ______   __    __ | ##____    ______          ______  | ##  ______  \n" +
                " |      \\ |       \\  /      ##      |  \\  |  \\| ##    \\ | ##    \\       |      \\    \\  |      \\ |  \\  |  \\| ##    \\  /      \\        |      \\ | ## /      \\ \n" +
                "  \\######\\| #######\\|  #######      | ##  | ##| #######\\| #######\\      | ######\\####\\  \\######\\| ##  | ##| #######\\|  ######\\        \\######\\| ##|  ######\\\n" +
                " /      ##| ##  | ##| ##  | ##      | ##  | ##| ##  | ##| ##  | ##      | ## | ## | ## /      ##| ##  | ##| ##  | ##| ##    ##       /      ##| ##| ##  | ##\n" +
                "|  #######| ##  | ##| ##__| ##      | ##__/ ##| ##  | ##| ##  | ##      | ## | ## | ##|  #######| ##__/ ##| ##__/ ##| ########      |  #######| ##| ##__/ ##\n" +
                " \\##    ##| ##  | ## \\##    ##       \\##    ##| ##  | ##| ##  | ##      | ## | ## | ## \\##    ## \\##    ##| ##    ## \\##     \\       \\##    ##| ## \\##    ##\n" +
                "  \\####### \\##   \\##  \\#######        \\######  \\##   \\## \\##   \\##       \\##  \\##  \\##  \\####### _\\####### \\#######   \\#######        \\####### \\##  \\###### \n" +
                "                                                                                                |  \\__| ##                                                  \n" +
                "                                                                                                 \\##    ##                                                  \n" +
                "                                                                                                  \\######                                                   \n");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    private static void drawTutorial1() {
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
        System.out.println("The game plays like tic-tac-toe. Each section of the main board is assigned a number. "
                            + "\nUse these numbers to choose a sub-board at the start of the game.");

    }
    private static void drawTutorial2() {
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
        System.out.println("However, each sub-board is further split into tiles from 1-9. Players take turns using these numbers to choose a spot to mark during the game." +
                           "\nThe tile a player selects corresponds to a sub-board on the larger board that the next player must play on." +
                           "\nWin 3 sub-boards horizontally, vertically, or diagonally to win the game." +
                           "\nOnce a sub-board has been won, it cannot be selected again. If a player happens to select a tile on another board that corresponds to an already won sub-board, " +
                           "\nthe next player will be able to choose any sub-board to play on." +
                           "\nSub-boards can end in ties, and the main board can also end in a tie.");
    }
}
