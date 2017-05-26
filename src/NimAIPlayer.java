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
public class NimAIPlayer extends NimPlayer implements Testable {
    // you may further extend a class or implement an interface
    // to accomplish the task in Section 2.3

    //NimAIPlayer is extends from NimPlayer.

    public NimAIPlayer(String username, String lastname, String firstname, boolean isAI) {
        super(username, lastname, firstname, isAI);
    }

    //AI player can playing NimGame
    //for most conditions, AI can win the game
    public int playingNim(int stonenum, int upperbound) {
        int removeNumber;
        //while the input is valid, execute the operation
        while (true) {
            printNimstoneinfo(stonenum);
            System.out.println(firstname + "'s turn - remove how many?");
            int a = stonenum % (upperbound + 1);
            if (a == 0) {
                removeNumber = upperbound;
            } else if (a == 1) {
                removeNumber = 1;
            } else {
                removeNumber = a - 1;
            }

            if (!checkInvalidinput(stonenum, removeNumber, upperbound)) {
                break;
            }
            printInvalidInfo(stonenum, upperbound);
        }
        return removeNumber;
    }

    public String advancedMove(boolean[] available, String lastMove) {
        // the implementation of the victory
        // guaranteed strategy designed by you
        String move = "";

        return move;
    }

    //AI player can playing AdvancedGame
    //AI cannot make sure to win the game
    public int[] playingAdvanced(boolean stone[], int stonenum) {
        int[] removeNumber = new int[2];
        removeNumber[0] = 0;
        removeNumber[1] = 0;
        //while the input is valid, execute the operation
        while (true) {
            try {
                printAdvancedinfo(stone, stonenum);
                System.out.println(firstname + "'s turn - which to remove?");
                for (int i = 0; i < stone.length; i++) {
                    if (stone[i] == true) {
                        removeNumber[0] = i + 1;
                        removeNumber[1] = 1;
                        return removeNumber;
                    }
                }
            } catch (Exception e) {
                printAdvancedInvalidInfo();
                continue;
            }
        }
    }
}
