/**
 * Created by Shen YI(844373) syi2 on 2017/4/27.
 */

public class NimGame extends Game{

    public NimGame(int initialstones, int upperbound, NimPlayer player1, NimPlayer player2) {
        super(initialstones, upperbound, player1, player2);
    }

    protected void printInitialinfo(int initialstones,NimPlayer player1,NimPlayer player2){
        System.out.println();
        System.out.println("Initial stone count: " + initialstones);
        System.out.println("Maximum stone removal: " + upperbound);
        System.out.println("Player 1: " + player1.getFirstname() + " " + player1.getLastname());
        System.out.println("Player 2: " + player2.getFirstname() + " " + player2.getLastname());
    }

    protected void playingGame(NimPlayer player1, NimPlayer player2){
        printInitialinfo(initialstones,player1,player2);
        //To define the active player, which is going to play next, or win.
        int turn = 0;

        while (!checkEmpty()) {
            int removeNum = 0;
            if (turn % 2 == 0) {
                removeNum = player1.playingNim(stonenum,upperbound);
                removeStone(removeNum);
                this.winner = player2;
                this.loser = player1;
            } else {
                removeNum = player2.playingNim(stonenum,upperbound);
                removeStone(removeNum);
                this.winner = player1;
                this.loser = player2;
            }
            turn++;
        }

        System.out.println();
        System.out.println("Game Over");

        System.out.println(winner.getFirstname() + " " + winner.getLastname() + " wins!");
    }
}
