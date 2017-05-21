/**
 * Created by Shen YI(844373) syi2 on 2017/3/20.
 */

public class NimPlayer {

    String username;
    String firstname;
    String lastname;
    int game;
    int win;

    //    define a NimPlayer class
    public NimPlayer(String username, String lastname, String firstname) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.game = 0;
        this.win = 0;
    }

    public String getUsername() {
        return this.username;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    //    To get the winning ratio of this player
    public double getRatio() {
        return ((double) this.win * 100 / this.game);
    }

    //    To print the winning ratio of this player in integer
    public String printRatio() {
        String ratio = String.valueOf(Math.round(getRatio())) + "%";
        while (ratio.length() < 4) {
            ratio = ratio + " ";
        }
        return ratio;
    }

    public String printPlayer() {
        String info;
        info = username + "," + lastname + "," + firstname + "," + game + " games," + win + " wins";
        return info;
    }

    //    To print the number of games this player has played in two-digit
    private String printGame() {
        String game = String.valueOf(this.game);
        while (game.length() < 2) {
            game = "0" + game;
        }
        return game;
    }

    //    print the rankings information of this player
    public String printRankings() {
        String rank;
        rank = printRatio() + " | " + printGame() + " games | " + lastname + " " + firstname;
        return rank;
    }

    //    update this player's first name and last name
    public void edit(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    //    reset the statistics of this player
    public void reset() {
        updateGame(0);
        updateWin(0);
    }

    //update this player's statistics win one more games
    //One more games and one more wins
    public void Win() {
        this.game++;
        this.win++;
    }

    //update this player's statistics lose one more games
    //One more games without more wins
    public void Lose() {
        this.game++;
    }

    public String getUpdatefile() {
        String players = username + "," + lastname + "," + firstname + "," + game + "," + win;
        return players;
    }

    public void updateWin(int win) {
        this.win = win;
    }

    public void updateGame(int game) {
        this.game = game;
    }

    public int playingNim(int num) {
        printNimstoneinfo(num);
        System.out.println(lastname + "'s turn - remove how many?");
        int removeNumber = Nimsys.keyboard.nextInt();
        return removeNumber;
    }

    private void printNimstoneinfo(int stonenum) {
        System.out.println();
        System.out.print(stonenum + " stones left:");
        for (int i = 0; i < stonenum; i++) {
            System.out.print(" *");
        }
        System.out.println();
    }
}