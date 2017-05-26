/**
 * Created by Shen YI(844373) syi2 on 2017/4/27.
 */

public class NimGame extends Game{

    public NimGame(int initialstones, int upperbound) {
        super(initialstones, upperbound);
    }

    //the NimGame process realized here, make player1 and player2 to playing this game
    protected void playingGame(NimPlayer player1, NimPlayer player2){
        printInitialinfo(initialstones,player1,player2);
        //To define the active player, which is going to play next, or win.
        int turn = 0;

        //if there is stones left, continue the game
        while (!checkEmpty()) {
            int removeNum;
            if (turn % 2 == 0) {
                removeNum = player1.playingNim(stonenum,upperbound);
                removeStone(removeNum);
                //if the game end now, player2 will win the game
                this.winner = player2;
                this.loser = player1;
            } else {
                removeNum = player2.playingNim(stonenum,upperbound);
                removeStone(removeNum);
                //if the game end now, player1 will win the game

                this.winner = player1;
                this.loser = player2;
            }
            //after a player's movement, it is the other player's turn
            turn++;
        }

        //if there is no stone left, game over.
        System.out.println();
        System.out.println("Game Over");

        //print the result of this game
        System.out.println(winner.getFirstname() + " " + winner.getLastname() + " wins!");
    }

    //print the initial game information at the beginning of the game
    protected void printInitialinfo(int initialstones,NimPlayer player1,NimPlayer player2){
        System.out.println();
        System.out.println("Initial stone count: " + initialstones);
        System.out.println("Maximum stone removal: " + upperbound);
        System.out.println("Player 1: "
                + player1.getFirstname() + " " + player1.getLastname());
        System.out.println("Player 2: "
                + player2.getFirstname() + " " + player2.getLastname());
    }

}
