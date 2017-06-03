
/*
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
        String name1, name2;

        System.out.print("Please set the window to fullscreen. Press ENTER to continue... "); read.nextLine();
        drawIntro();
        drawTutorial1();
        System.out.print("\nPress ENTER to continue... "); read.nextLine();
        drawTutorial2();
        System.out.print("\nPress ENTER to continue... "); read.nextLine();
        System.out.print("\f");
        
        System.out.print("Player 1 is X. Enter name: "); name1 = read.nextLine(); name1 = name1.trim();
        while(!(name1.length()<22&&name1.length()>0)){
            System.out.print("Invalid entry. Please enter a name between 1 and 21 characters: "); name1 = read.nextLine(); name1 = name1.trim();
        }
        System.out.print("Player 2 is O. Enter name: "); name2 = read.nextLine(); name2 = name2.trim();
        while(!(name2.length()<22&&name2.length()>0)){
            System.out.print("Invalid entry. Please enter a name between 1 and 21 characters: "); name2 = read.nextLine(); name2 = name2.trim();
        }
        name1 = name1.trim();
        name2 = name2.trim();
        System.out.println("Press ENTER to start the game...");
        System.out.print("\f");

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                TTT[i][j] = new Board(count);
                count++;
            }
        }

        drawBoard(TTT, xWins, oWins, tied, 0, "44");
        String symb =  ((turn%2+1)==1)?"(X)":"(O)";
        System.out.println((turn%2+1==1?name1:name2) + "'s turn." + symb);

        int subBoard = -1;
        int tile = -1;
        System.out.print("Choose a sub-board: ");
        do  {
            try {
                subBoard = read.nextInt();
            } catch (InputMismatchException ex) {
                read.next();
            }
        }
        while (!isValidInput(subBoard));

        System.out.print("\f");
        drawBoard(TTT, xWins, oWins, tied, subBoard, findIndexOfSubBoard(subBoard));
        symb =  ((turn%2+1)==1)?"(X)":"(O)";
        System.out.println((turn%2+1==1?name1:name2) + "'s turn." + symb);
        System.out.println("Now at sub-board: "+subBoard);
        
        System.out.print("Choose a tile: ");
        do  {
            try {
                tile = read.nextInt();
            } catch (InputMismatchException ex) {
                read.next();
            }
        }
        while (!isValidInput(tile));

        String temp = findIndexOfSubBoard(subBoard);
        int c = Integer.parseInt(temp.substring(1));
        int r = Integer.parseInt(temp.substring(0,1));
        TTT[r][c].move(tile, (turn%2==0)?"X":"O");
        
        temp = findIndexOfSubBoard(tile);
        System.out.print("\f");
        drawBoard(TTT, xWins, oWins, tied, tile, temp);

        while(checkWinner(TTT) == 0 && checkWinner(TTT) != 3) {
            turn++;
            symb =  ((turn%2+1)==1)?"(X)":"(O)";
            System.out.println((turn%2+1==1?name1:name2) + "'s turn." + symb);

            if(searchWon(tile, won)) {
                System.out.print("Previous player chose a sub-board that is already won. Choose any other sub-board: ");
                tile = -1;
                boolean firstPass = true;
                do  {
                    try {
                        if (firstPass) firstPass = false;
                        else if(searchWon(tile, won)) System.out.print("Invalid move. Try again: ");
                        
                        tile = read.nextInt();
                    } catch (InputMismatchException ex) {
                        
                        read.next();
                    }
                }
                while (!isValidInput(tile)?true:searchWon(tile, won));
                /*tile = read.nextInt();
                while (tile < 1 || tile > 9 || searchWon(tile, won)) {
                    System.out.print("Invalid move. Try again: ");
                    tile = read.nextInt();
                }*/

            }
            subBoard = tile;
            temp = findIndexOfSubBoard(tile);
            System.out.print("\f");
            drawBoard(TTT, xWins, oWins, tied, tile, temp);
            symb =  ((turn%2+1)==1)?"(X)":"(O)";
            System.out.println((turn%2+1==1?name1:name2) + "'s turn." + symb);
            System.out.print("Now at sub-board " + subBoard + ". Please choose a tile within that sub-board: ");

            tile = -1;
            do  {
                try {
                    //idk if this works lol
                    if(searchWon(tile, won)) System.out.print("Invalid move. Try again: ");
                    tile = read.nextInt();
                } catch (InputMismatchException ex) {
                    
                    read.next();
                }
            }
            while (!isValidInput(tile)?true:searchWon(tile, won)?!TTT[r][c].move(tile, (turn % 2 == 0) ? "X" : "O"):false);
            /*tile = read.nextInt();

            while (tile < 1 || tile > 9) {
                System.out.print("Invalid move. Try again: ");
                tile = read.nextInt();
            }*/

            temp = findIndexOfSubBoard(subBoard);
            c = Integer.parseInt(temp.substring(1));
            r = Integer.parseInt(temp.substring(0,1));

            //flag to self
            while(!TTT[r][c].move(tile, (turn % 2 == 0) ? "X" : "O")) {
                System.out.print("Invalid move. Try again: ");

                tile = -1;
                do  {
                    try {
                        tile = read.nextInt();
                    } catch (InputMismatchException ex) {

                        read.next();
                    }
                }
                while (!isValidInput(tile)?true:searchWon(tile, won));

                /*tile = read.nextInt();
                while (tile < 1 || tile > 9) {
                    System.out.print("Invalid move. Try again: ");
                    tile = read.nextInt();
                }*/
                c = Integer.parseInt(temp.substring(1));
                r = Integer.parseInt(temp.substring(0,1));
            }

            won.add(TTT[r][c].checkBoard());

            if(won.get(won.size()-1) != 0) {
                if(won.get(won.size()-1) == 10) {
                    won.add(subBoard);
                    tied.add(subBoard);
                }
                else if(turn%2+1 == 1) {
                    xWins.add(subBoard);
                }
                else {
                    oWins.add(subBoard);
                }
            }
            
            if(won.contains(tile)) {
                System.out.print("\f");
                drawBoard(TTT, xWins, oWins, tied, subBoard, temp);
            }
            else {
                temp = findIndexOfSubBoard(tile);
                System.out.print("\f");
                drawBoard(TTT, xWins, oWins, tied, tile, temp);
            }

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
        if (checkWinner(TTT) == 3){
            System.out.println("No one won.");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(
                    "\f ________  __                                                              __ \n" +
                    "/        |/  |                                                            /  |\n" +
                    "########/ ##/   ______          ______    ______   _____  ____    ______  ## |\n" +
                    "   ## |   /  | /      \\        /      \\  /      \\ /     \\/    \\  /      \\ ## |\n" +
                    "   ## |   ## |/######  |      /######  | ######  |###### ####  |/######  |## |\n" +
                    "   ## |   ## |##    ## |      ## |  ## | /    ## |## | ## | ## |##    ## |##/ \n" +
                    "   ## |   ## |########/       ## \\__## |/####### |## | ## | ## |########/  __ \n" +
                    "   ## |   ## |##       |      ##    ## |##    ## |## | ## | ## |##       |/  |\n" +
                    "   ##/    ##/  #######/        ####### | #######/ ##/  ##/  ##/  #######/ ##/ \n" +
                    "                              /  \\__## |                                      \n" +
                    "                              ##    ##/                                       \n" +
                    "                               ######/                                        \n");
        }
        else {
            if(checkWinner(TTT) == 1) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("GAME OVER! " + name1 + " wins!");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("\fGAME OVER! " + name1 + " wins!");
                drawWinner(name1);
            }
            else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("GAME OVER! " + name2 + " wins!");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("\fGAME OVER! " + name2 + " wins!");
                drawWinner(name2);
            }
        }
    }

    //prints out winner screen
    private static void drawWinner(String winner){
        int length = (21-winner.length())/2;
        System.out.print("                                  ___________\n" +
                "                             .---'::'        `---.\n" +
                "                            (::::::'              )\n" +
                "                            |`-----._______.-----'|\n" +
                "                            |              :::::::|\n" +
                "                           .|               ::::::!-.\n" +
                "                           \\|               :::::/|/\n" +
                "                            |               ::::::|\n" +
                "                            |");
        for (int i = 0; i < length; i++){
            System.out.print(" ");
        }
        if(winner.length()%2==0)System.out.print(" ");
        System.out.print(winner);

        if(winner.length()<8){
            for (int i = 0; i < length-5; i++){
                System.out.print(" ");
                length--;
            }

        }
        for (int i = 0; i < length; i++){
            System.out.print(":");
        }
        System.out.print("|\n" +
                "                            |                :::::|\n" +
                "                            |               ::::::|\n" +
                "                            |              .::::::|\n" +
                "                            F              :::::::J\n" +
                "                             \\            :::::::/\n" +
                "                              `.        .:::::::'\n" +
                "                                `-._  .::::::-'\n" +
                "____________________________________|  \"\"\"|\"_________________________________________\n" +
                "                                    |  :::|\n" +
                "                                    F   ::J\n" +
                "                                   /     ::\\\n" +
                "                              __.-'      :::`-.__\n" +
                "                             (_           ::::::_)\n" +
                "                               `\"\"\"---------\"\"\"'");
    }

    //checks validity of input
    private static boolean isValidInput(int input){
        if (input > 0 && input < 10) return true;
        else {
            System.out.print("Invalid move. Try again: ");
            return false;
        }
    }
    //checks if the board is won, in which case it would be contained in the ArrayList won
    private static boolean searchWon(int s, ArrayList<Integer> won) {
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
    private static void drawBoard(Board[][] TTT, ArrayList<Integer> x, ArrayList<Integer> o, ArrayList<Integer> t, int subBoard, String temp) {
        int rowNum = 0;
        String highlighter = "@"; String normal = "|";
        int r = Integer.parseInt(temp.substring(0,1));
        int c = Integer.parseInt(temp.substring(1));
        //top border
        for(int i = 1; i <= 39; i++) {
                if(i <= c*13+13 && i > c*13 &&
                    (subBoard <= 3 && subBoard >= 1))
                    System.out.print(highlighter);
                else
                    System.out.print(normal);
        }
        System.out.println();
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                for(int k = 0; k < 3; k++) {
                    TTT[i][k].drawRow(rowNum % 3, subBoard, temp, highlighter, normal);
                }
                System.out.print((rowNum==1)?"\t\tPlayer 1 won boards: " + x:(rowNum==2)?"\t\tPlayer 2 won boards: " + o:
                                    (rowNum==3)?"\t\tTied boards: " + t:"");
                System.out.println();
                rowNum++;
                
                if(j < 2) {
                    for(int m = 0; m < 3; m++) {
                        if(c == m && r == i) {
                            System.out.print(highlighter+"-----------"+highlighter);
                        }
                        else {
                            System.out.print(normal+"-----------"+normal);
                        }
                    }
                    System.out.println();
                }
                //System.out.println((j==2)?"":"\n"+normal+"-----------"+normal+normal+"-----------"+normal+normal+"-----------"+normal);
            }
            
            for(int l = 1; l <= 39; l++) {
                if(l <= c*13+13 && l > c*13 &&
                        (r == i || r-1 == i))
                    System.out.print(highlighter);
                else
                    System.out.print(normal);
            }
            System.out.println();
        }
    }
    

    private static void drawIntro() {
        /*System.out.println("\f2+5 = " + (2 + 5));
        System.out.println("Java is cool!");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            Thread.sleep(3000);
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
            Thread.sleep(2000);
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
                "                                                  \n");
        try {
            Thread.sleep(2000);
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
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("\n" +
                "  ______   __  __ \n" +
                " /      \\ |  \\|  \\\n" +
                "|  ######\\| ## \\##\n" +
                "| ##__| ##| ##|  \\\n" +
                "| ##    ##| ##| ##\n" +
                "| ########| ##| ##\n" +
                "| ##  | ##| ##| ##\n" +
                "| ##  | ##| ##| ##\n" +
                " \\##   \\## \\## \\##\n\n\n");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }



    private static void drawTutorial1() {
        System.out.println("*********ULTIMATE TIC TAC TOE**********\n"+
                "#######################################\n"+
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
        System.out.println("\n#######################################\n"+
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
