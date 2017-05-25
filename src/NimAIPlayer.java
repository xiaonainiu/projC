import java.util.InputMismatchException;

/**
 * Created by Shen YI(844373) syi2 on 2017/5/21.
 */
public class NimAIPlayer extends NimPlayer {
    public NimAIPlayer(String username, String lastname, String firstname, boolean isAI) {
        super(username, lastname, firstname, isAI);
    }
//    private void playingNim(int num){
//
//    }

    public int playingNim(int stonenum,int upperbound) {
        int removeNumber;
        while (true){
            printNimstoneinfo(stonenum);
            System.out.println(firstname + "'s turn - remove how many?");
            int a = stonenum%(upperbound+1);
            if (a==0){
                removeNumber=upperbound;
            }else if(a==1){
                removeNumber = 1;
            } else {
                removeNumber = a-1;
            }

            if (!checkInvalidinput(stonenum,removeNumber,upperbound)){
                break;
            }
            printInvalidInfo(stonenum,upperbound);
        }
        return removeNumber;
    }
}
