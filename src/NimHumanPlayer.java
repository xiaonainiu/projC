import java.util.InputMismatchException;

/**
 * Created by Shen YI(844373) syi2 on 2017/5/21.
 */
public class NimHumanPlayer extends NimPlayer {
    public NimHumanPlayer(String username, String lastname, String firstname, boolean isAI) {
        super(username, lastname, firstname, isAI);
    }

    //Human player can playing NimGame
    public int playingNim(int stonenum,int upperbound) {
        int removeNumber;

        //while the input is valid, execute the operation
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

    //Hunman player can playing AdvancedGame
    public int[] playingAdvanced(boolean stone[],int stonenum){

        int[] removeNumber = new int[2];
        removeNumber[0]=0;
        removeNumber[1]=0;

        //while the input is valid, execute the operation
        while (true){
            try{
                printAdvancedinfo(stone,stonenum);
                System.out.println(firstname + "'s turn - which to remove?");
                //player input the position and the amount of stone
                //the first number is the position, the second number is the amount of stone
                //for example, "3 2" means remove "2" stones from stone number "3"
                String command = Nimsys.keyboard.nextLine();
                String[] commandsplit = command.split("\\s+");
                removeNumber[0]=Integer.valueOf(commandsplit[0]);
                removeNumber[1]=Integer.valueOf(commandsplit[1]);
                if (checkAdvancedInvalidinput(stone,removeNumber)){
                    printAdvancedInvalidInfo();
                }else {
                    return removeNumber;
                }

            }catch (Exception e){
                printAdvancedInvalidInfo();
                continue;
            }
        }
    }


}
