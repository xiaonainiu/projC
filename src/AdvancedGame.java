/**
 * Created by Shen YI(844373) syi2 on 2017/5/25.
 */
public class AdvancedGame extends Game{
    //in advanced game, stones need be ordered
    //we order the stones in a array named stone
    private boolean[] stone;

    public AdvancedGame(int initialstones){
        super(initialstones,2);
        //initialize the array stone
        this.stone = new boolean[initialstones];
        for (int i=0;i<initialstones;i++){
            stone[i]=true;
        }
    }

    //the NimGame process realized here, make player1 and player2 to playing this game
    protected void playingGame(NimPlayer player1, NimPlayer player2){
        printInitialinfo(initialstones,player1,player2);
        //To define the active player, which is going to play next, or win.
        int turn = 0;

        //if there is stones left, continue the game
        while (!checkEmpty()) {
            //player will input the position and the amount of stone in removeNum
            //the first number is the position, the second number is the amount of stone
            //for example, "3 2" means remove "2" stones from stone number "3"
            int[] removeNum = new int[2];
            removeNum[0]=0;
            removeNum[1]=0;
            if (turn % 2 == 0) {
                removeNum = player1.playingAdvanced(stone,stonenum);
                //get the player's movement and remove the stone if it is exist
                if (removeNum[1]==1){
                    stone[removeNum[0]-1]=false;
                    stonenum=stonenum-1;
                }else if (removeNum[1]==2){
                    stone[removeNum[0]-1]=false;
                    stone[removeNum[0]]=false;
                    stonenum=stonenum-2;
                }else {
                    System.out.println("Invalid move.");
                }
                //if the game end now, player2 will win the game
                this.winner = player1;
                this.loser = player2;
            } else {
                removeNum = player2.playingAdvanced(stone,stonenum);
                //get the player's movement and remove the stone if it is exist
                if (removeNum[1]==1){
                    stone[removeNum[0]-1]=false;
                    stonenum=stonenum-1;
                }else if (removeNum[1]==2){
                    stone[removeNum[0]-1]=false;
                    stone[removeNum[0]]=false;
                    stonenum=stonenum-2;
                }else {
                    System.out.println("Invalid move.");
                }
                //if the game end now, player2 will win the game
                this.winner = player2;
                this.loser = player1;
            }
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
        for (int i = 0;i<initialstones;i++){
            stone[i]=true;
        }
        System.out.println();
        System.out.println("Initial stone count: "+initialstones);
        System.out.print("Stones display:");
        for (int i = 0;i<initialstones;i++){
            if (stone[i]==true){
                System.out.print(" <"+(i+1)+",*>");
            }else {
                System.out.print(" <"+(i+1)+",x>");
            }
        }
        System.out.println();
        System.out.println("Player 1: " + player1.getFirstname() + " " + player1.getLastname());
        System.out.println("Player 2: " + player2.getFirstname() + " " + player2.getLastname());
    }

}
