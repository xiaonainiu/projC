/*
	NimAIPlayer.java
	
	This class is provided as a skeleton code for the tasks of 
	Sections 2.3, 2.4 and 2.5 in Project C. Add code (do NOT delete any) to it
	to finish the tasks. 
	
	Coded by: Jin Huang
	Modified by: Jianzhong Qi, 29/04/2015
*/

/**
 * Edited by Shen YI(844373) syi2 on 2017/5/21.
 */
public class NimAIPlayer  extends NimPlayer implements Testable{
	// you may further extend a class or implement an interface
	// to accomplish the task in Section 2.3	

	public NimAIPlayer(String username, String lastname, String firstname, boolean isAI) {
        super(username, lastname, firstname, isAI);
    }

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
	
	public String advancedMove(boolean[] available, String lastMove) {
		// the implementation of the victory
		// guaranteed strategy designed by you
		String move = "";
		
		return move;
	}

    public int[] playingAdvanced(boolean stone[], int stonenum){
        return null;
    }
}
