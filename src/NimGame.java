/**
 * Created by Shen YI(844373) syi2 on 2017/4/27.
 */

public class NimGame {
    private int stonenum;
    private int upperbound;
    NimPlayer player1;
    NimPlayer player2;
    NimPlayer winner;
    NimPlayer loser;

    //To start a game with defined arguments
    //initial stone number, upper bound of stones can be remove at once, player 1 and player 2.
    public NimGame(int initialstones, int upperbound, NimPlayer player1, NimPlayer player2) {
        this.stonenum = initialstones;
        this.upperbound = upperbound;
        this.player1 = player1;
        this.player2 = player2;
        System.out.println();
        System.out.println("Initial stone count: " + initialstones);
        System.out.println("Maximum stone removal: " + upperbound);
        System.out.println("Player 1: " + player1.getLastname() + " " + player1.getFirstname());
        System.out.println("Player 2: " + player2.getLastname() + " " + player2.getFirstname());

        printNimstoneinfo();

        //To define the active player, which is going to play next, or win.
        int turn = 0;
        NimPlayer activeplayer = null;

        while (!checkEmpty()) {
            if (turn % 2 == 0) {
                while (!playingNim(player1)) ;
                activeplayer = player2;
            } else {
                while (!playingNim(player2)) ;
                activeplayer = player1;
            }
            turn++;
        }

        System.out.println();
        System.out.println("Game Over");

        //Update the players' information
        if (turn % 2 == 0) {
            this.winner = player1;
            this.loser = player2;
        } else {
            this.winner = player2;
            this.loser = player1;
        }

        System.out.println(activeplayer.getLastname() + " " + activeplayer.getFirstname() + " wins!");
        return;
    }

    //Playing process.
    private boolean playingNim(NimPlayer player) {
        System.out.println(player.getLastname() + "'s turn - remove how many?");
        int removenum = Nimsys.keyboard.nextInt();

        if (checkValidinput(removenum)) {
            System.out.println();
            System.out.println("Invalid move. You must remove between 1 and " + min() + " stones.");
            printNimstoneinfo();
            return false;
        } else {
            removeStone(removenum);
            return true;
        }
    }

    //To remove stones, if there is stones left, show the stone's information
    private void removeStone(int removenum) {
        stonenum = stonenum - removenum;
        if (!checkEmpty()) {
            printNimstoneinfo();
        }
    }

    //To check if there is no stone left
    private boolean checkEmpty() {
        if (stonenum == 0) return true;
        else {
            return false;
        }
    }

    //To check if the input number is valid
    private boolean checkValidinput(int removenum) {
        return (removenum > upperbound || removenum < 1 || removenum > stonenum);
    }

    //show the stone's information
    private void printNimstoneinfo() {
        System.out.println();
        System.out.print(stonenum + " stones left:");
        for (int i = 0; i < stonenum; i++) {
            System.out.print(" *");
        }
        System.out.println();

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
