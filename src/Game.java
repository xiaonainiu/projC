/**
 * Created by es on 2017/5/25.
 */
public abstract class Game {
    //for a game, it has a current number of stones
    protected int stonenum;
    //a upper bound to limit players to remove it
    protected int upperbound;
    //a winner and a loser
    protected NimPlayer winner;
    protected NimPlayer loser;
    //a initial numbers of stone
    protected int initialstones;

    //To create a game with defined arguments
    //initial number of stones, upper bound of stones can be remove at once
    protected Game(int initialstones, int upperbound) {
        this.stonenum = initialstones;
        this.upperbound = upperbound;
        this.initialstones = initialstones;
    }

    //let two players to play this game
    protected abstract void playingGame(NimPlayer player1, NimPlayer player2);

    //print the initial game information at the beginning of the game
    protected abstract void printInitialinfo
    (int initialstones,NimPlayer player1, NimPlayer player2);

    //remove the stone by the player and count the current number of stones
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

    //get the winner and loser of this game
    protected NimPlayer getWinner() {
        return winner;
    }

    protected NimPlayer getLoser() {
        return loser;
    }
}
