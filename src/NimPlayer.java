/**
 * Created by Shen YI(844373) syi2 on 2017/3/20.
 */

public abstract class NimPlayer {

    String username;
    String firstname;
    String lastname;
    int game;
    int win;
    boolean isAI;
    //boolean isAI is use to identify the type of player.
    //if isAI = true, this is a AI player.
    //if isAI = false, this is not a AI player.

    //define a NimPlayer class
    protected NimPlayer(String username, String lastname, String firstname, boolean isAI) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.game = 0;
        this.win = 0;
        this.isAI = isAI;
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

    //show the information of this NimPlayer
    public String printPlayer() {
        String info;
        info = username + "," + firstname + ","
                + lastname + "," + game + " games," + win + " wins";
        return info;
    }

    //To print the number of games this player has played in two-digit
    private String printGame() {
        String game = String.valueOf(this.game);
        while (game.length() < 2) {
            game = "0" + game;
        }
        return game;
    }

    //print the rankings information of this player
    public String printRankings() {
        String rank;
        rank = printRatio() + " | " + printGame() + " games | "
                + firstname + " " + lastname;
        return rank;
    }

    //update this player's first name and last name
    public void edit(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    //reset the statistics of this player
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

    //get all the information of this player into a string
    //use to update the information to the file players.dat
    public String getUpdatefile() {
        String players = username + "," + lastname + ","
                + firstname + "," + game + "," + win+","+isAI;
        return players;
    }

    //after read information from the file players.dat, update the win and game.
    public void updateWin(int win) {
        this.win = win;
    }

    public void updateGame(int game) {
        this.game = game;
    }

    //player can playing NimGame
    protected abstract int playingNim(int stonenum,int upperbound);

    //player can playing AdvancedGame
    protected abstract int[] playingAdvanced(boolean stone[], int stonenum);

    //in NimGame, use these method

    //print the stone information to guide the player make a right dicision
    protected void printNimstoneinfo(int stonenum) {
        System.out.println();
        System.out.print(stonenum + " stones left:");
        for (int i = 0; i < stonenum; i++) {
            System.out.print(" *");
        }
        System.out.println();
    }

    //check if the input is invalid
    protected boolean checkInvalidinput(int stonenum,int removenum, int upperbound) {
        return (removenum > upperbound || removenum < 1 || removenum > stonenum);
    }

    //get the smaller number from upper bound and stone number
    private int min(int upperbound,int stonenum) {
        if (upperbound <= stonenum) return upperbound;
        else return stonenum;
    }

    //player cannot remove such amount of stones is it is invalid
    protected void printInvalidInfo(int stonenum,int upperbound){
        System.out.println();
        System.out.println("Invalid move. You must remove between 1 and "
                + min(upperbound,stonenum) + " stones.");
    }

    //in AdvancedGame, use these method

    //print the stone information to guide the player make a right dicision
    protected void printAdvancedinfo(boolean stone[],int stonenum){
        System.out.println();
        System.out.print(stonenum+" stones left:");
        for (int i = 0;i<stone.length;i++){
            if (stone[i]==true){
                System.out.print(" <"+(i+1)+",*>");
            }else {
                System.out.print(" <"+(i+1)+",x>");
            }
        }
        System.out.println();
    }

    //    //check if the input is invalid
    protected boolean checkAdvancedInvalidinput(boolean stone[], int removeNumber[]){
        if (removeNumber[1]==1){
            if (stone[removeNumber[0]-1]==true){
                return false;
            }else {
                return true;
            }
        }else if (removeNumber[1]==2){
            if (stone[removeNumber[0]-1]==true&&stone[removeNumber[0]]==true){
                return false;
            }else {
                return true;
            }
        }else {
            return true;
        }
    }

    //player cannot remove such amount of stones is it is invalid
    protected void printAdvancedInvalidInfo(){
        System.out.println("Invalid move.");
    }
}