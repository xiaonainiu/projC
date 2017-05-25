/**
 * Created by es on 2017/5/25.
 */
public abstract class Game {
    protected int stonenum;
    protected int upperbound;
    protected NimPlayer winner;
    protected NimPlayer loser;
    protected int initialstones;
//    private NimPlayer activeplayer;

    //To start a game with defined arguments
    //initial stone number, upper bound of stones can be remove at once, player 1 and player 2.
    public Game(int initialstones, int upperbound, NimPlayer player1, NimPlayer player2) {
        this.stonenum = initialstones;
        this.upperbound = upperbound;
        this.initialstones = initialstones;
    }

    protected abstract void playingGame(NimPlayer player1, NimPlayer player2);

    protected abstract void printInitialinfo(int initialstones,NimPlayer player1, NimPlayer player2);

    //To remove stones, if there is stones left, show the stone's information
    protected void removeStone(int removenum) {
        stonenum = stonenum - removenum;
    }

    //To check if there is no stone left
    protected boolean checkEmpty() {
        if (stonenum == 0) return true;
        else {
            return false;
        }
    }

    public NimPlayer getWinner() {
        return winner;
    }

    public NimPlayer getLoser() {
        return loser;
    }
}
