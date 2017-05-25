import java.util.InputMismatchException;

/**
 * Created by Shen YI(844373) syi2 on 2017/5/21.
 */
public class NimHumanPlayer extends NimPlayer {
    public NimHumanPlayer(String username, String lastname, String firstname, boolean isAI) {
        super(username, lastname, firstname, isAI);
    }
    public int playingNim(int stonenum,int upperbound) {


        int removeNumber;
        while (true){
            try{
                printNimstoneinfo(stonenum);
                System.out.println(firstname + "'s turn - remove how many?");
                removeNumber = Nimsys.keyboard.nextInt();
            }catch (InputMismatchException e){
                printInvalidInfo(stonenum,upperbound);
                Nimsys.keyboard.next();
                continue;
            }

            if (!checkInvalidinput(stonenum,removeNumber,upperbound)){
                break;
            }
            printInvalidInfo(stonenum,upperbound);
        }
        return removeNumber;
    }

    public int[] playingAdvanced(boolean stone[],int stonenum){

        int[] removeNumber = new int[2];
        removeNumber[0]=0;
        removeNumber[1]=0;
        while (true){
            try{
                printAdvancedinfo(stone,stonenum);
                System.out.println(firstname + "'s turn - which to remove?");
                String command = Nimsys.keyboard.nextLine();
                String[] commandsplit = command.split("\\s+");
                removeNumber[0]=Integer.valueOf(commandsplit[0]);
                removeNumber[1]=Integer.valueOf(commandsplit[1]);
                if (removeNumber[1]==1){
                    if (stone[removeNumber[0]-1]==true){
                        return removeNumber;
                    }
                }else if (removeNumber[1]==2){
                    if (stone[removeNumber[0]-1]==true&&stone[removeNumber[0]]==true){
                        return removeNumber;
                    }
                }
                printInvalidInfo();
            }catch (Exception e){
                printInvalidInfo();
                continue;
            }
        }
    }

    protected void printInvalidInfo(){
        System.out.println("Invalid move.");
    }
}
