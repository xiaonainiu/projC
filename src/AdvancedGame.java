/**
 * Created by Shen YI(844373) syi2 on 2017/5/25.
 */
public class AdvancedGame extends Game{
    private boolean[] stone;
//    private NimPlayer winner;
//    private NimPlayer loser;

    public AdvancedGame(int initialstones, NimPlayer player1, NimPlayer player2){
        super(initialstones,2,player1,player2);
        this.stone = new boolean[initialstones];
        for (int i=0;i<initialstones;i++){
            stone[i]=true;
        }
    }

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

    protected void playingGame(NimPlayer player1, NimPlayer player2){
        printInitialinfo(initialstones,player1,player2);

        int turn = 0;

        while (!checkEmpty()) {
            int[] removeNum = new int[2];
            removeNum[0]=0;
            removeNum[1]=0;
            if (turn % 2 == 0) {
                removeNum = player1.playingAdvanced(stone,stonenum);
                if (removeNum[1]==1){
                    stone[removeNum[0]-1]=false;
                    stonenum=stonenum-1;
                }else if (removeNum[1]==2){
                    stone[removeNum[0]-1]=false;
                    stone[removeNum[0]]=false;
                    stonenum=stonenum-2;
                }else {
                    System.out.println("WRONG at AdvancedGame playingGame!");
                }
                this.winner = player1;
                this.loser = player2;
            } else {
                removeNum = player2.playingAdvanced(stone,stonenum);
                if (removeNum[1]==1){
                    stone[removeNum[0]-1]=false;
                    stonenum=stonenum-1;
                }else if (removeNum[1]==2){
                    stone[removeNum[0]-1]=false;
                    stone[removeNum[0]]=false;
                    stonenum=stonenum-2;
                }else {
                    System.out.println("WRONG at AdvancedGame playingGame!");
                }
                this.winner = player2;
                this.loser = player1;
            }
            turn++;
        }

        System.out.println();
        System.out.println("Game Over");

        System.out.println(winner.getFirstname() + " " + winner.getLastname() + " wins!");
    }
}
