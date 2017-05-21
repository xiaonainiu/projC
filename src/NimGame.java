import java.util.InputMismatchException;

/**
 * Created by Shen YI(844373) syi2 on 2017/4/27.
 */

public class NimGame {
    private int stonenum;
    private int upperbound;
    //    private NimPlayer player1;
//    private NimPlayer player2;
    private NimPlayer winner;
    private NimPlayer loser;
//    private NimPlayer activeplayer;

    //To start a game with defined arguments
    //initial stone number, upper bound of stones can be remove at once, player 1 and player 2.
    public NimGame(int initialstones, int upperbound, NimPlayer player1, NimPlayer player2) {
        this.stonenum = initialstones;
        this.upperbound = upperbound;
        System.out.println();
        System.out.println("Initial stone count: " + initialstones);
        System.out.println("Maximum stone removal: " + upperbound);
        System.out.println("Player 1: " + player1.getLastname() + " " + player1.getFirstname());
        System.out.println("Player 2: " + player2.getLastname() + " " + player2.getFirstname());

//        printNimstoneinfo();

        //To define the active player, which is going to play next, or win.
        int turn = 0;

        while (!checkEmpty()) {
            int removeNum = 0;
            if (turn % 2 == 0) {
                while (true) {
                    try{
                        removeNum = player1.playingNim(stonenum);
                    }catch (InputMismatchException e){
                        printInvalidInfo();
                        Nimsys.keyboard.next();
                        continue;
                    }

                    if (!checkInvalidinput(removeNum)){
                        break;
                    }
                    printInvalidInfo();
                }
                removeStone(removeNum);
                this.winner = player2;
                this.loser = player1;
            } else {
                while (true) {
                    try{
                        removeNum = player2.playingNim(stonenum);
                    }catch (InputMismatchException e){
                        printInvalidInfo();
                        Nimsys.keyboard.next();
                        continue;
                    }
                    if (!checkInvalidinput(removeNum)){
                        break;
                    }
                    printInvalidInfo();
                }
                removeStone(removeNum);
                this.winner = player1;
                this.loser = player2;
            }
            turn++;
        }

        System.out.println();
        System.out.println("Game Over");

        System.out.println(winner.getLastname() + " " + winner.getFirstname() + " wins!");
        return;
    }

    //To remove stones, if there is stones left, show the stone's information
    private void removeStone(int removenum) {
        stonenum = stonenum - removenum;
//        if (!checkEmpty()) {
//            printNimstoneinfo();
//        }
    }

    //To check if there is no stone left
    private boolean checkEmpty() {
        if (stonenum == 0) return true;
        else {
            return false;
        }
    }

    //To check if the input number is valid
    private boolean checkInvalidinput(int removenum) {
        return (removenum > upperbound || removenum < 1 || removenum > stonenum);
    }


    private void printInvalidInfo(){
        System.out.println();
        System.out.println("Invalid move. You must remove between 1 and " + min() + " stones.");
//        printNimstoneinfo();
    }

    //Output the upper bound
    private int min() {
        if (upperbound <= stonenum) return upperbound;
        else return stonenum;
    }

    public NimPlayer getWinner() {
        return winner;
    }

    public NimPlayer getLoser() {
        return loser;
    }
}
