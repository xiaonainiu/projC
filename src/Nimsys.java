/**
 * Created by Shen YI(844373) syi2 on 2017/3/26.
 */

import java.util.Scanner;

public class Nimsys {
    static Scanner keyboard;
    static NimPlayer[] playerlist;

    //    scan the command
    private String scanCommand() {
        System.out.println();
        System.out.print("$");
        String command = "";
        while (command.equals("")) {
            command = keyboard.nextLine();
        }
        return command;
    }

    //Identify the command, split command in parts
    //Invoke different method depends on different command
    private void identifyCommand(String command) {

        String[] commandsplit = command.split("\\s+");

        if (commandsplit[0].equals("addplayer")) {
            addPlayer(commandsplit[1]);
        } else if (commandsplit[0].equals("removeplayer")) {
            if (commandsplit[0].length() == command.length()) {
                removePlayer(null);
            } else {
                removePlayer(commandsplit[1]);
            }
        } else if (commandsplit[0].equals("editplayer")) {
            editPlayer(commandsplit[1]);
        } else if (commandsplit[0].equals("resetstats")) {
            if (commandsplit[0].length() == command.length()) {
                resetStats(null);
            } else {
                resetStats(commandsplit[1]);
            }
        } else if (commandsplit[0].equals("displayplayer")) {
            if (commandsplit[0].length() == command.length()) {
                displayPlayer(null);
            } else {
                displayPlayer(commandsplit[1]);
            }
        } else if (commandsplit[0].equals("rankings")) {
            if (commandsplit[0].length() == command.length()) {
                rankings(null);
            } else {
                rankings(commandsplit[1]);
            }
        } else if (commandsplit[0].equals("startgame")) {
            startGame(commandsplit[1]);
        } else if (commandsplit[0].equals("exit")) {
            System.out.println();
            System.exit(0);
        } else {
            System.out.println("command not exist");
        }
    }

    //Check the index of aim username.
    //If the username is exist, return the index of the user.
    //If the username is not exist, return the index the user should be.
    // The user is ordered in alphabetical order in the user list
    //If the username is not exist and the user list is full, return 100.
    private int checkExist(String username) {
        for (int i = 0; i < 100; i++) {
            NimPlayer player = playerlist[i];
            if (player != null) {
                if (username.compareTo(playerlist[i].username) <= 0) {
                    return i;
                }
            }
            if (player == null) return i;
        }
        return 100;
    }

    //Add player into the user list in the right index.
    // The user is ordered in alphabetical order in the user list.
    private void addPlayer(String playerinfo) {
        String[] argu = playerinfo.split(",");
        String username = argu[0];

        int index = checkExist(username);
        if (index == 100) {
            System.out.println("full");
        } else if (playerlist[index] == null) {
            playerlist[index] = new NimPlayer(argu[0], argu[1], argu[2]);
        } else if (playerlist[index].username.equals(username)) {
            System.out.println("The player already exists.");
        } else {
            for (int i = 99; i > index; i--) {
                if (playerlist[i - 1] != null) {
                    playerlist[i] = playerlist[i - 1];
                }
            }
            playerlist[index] = new NimPlayer(argu[0], argu[1], argu[2]);
        }
    }

    //    Check the commend first.
//    Then, remove all players or remove the aim player if exist.
    private void removePlayer(String playerinfo) {
        String username = playerinfo;

        if (username == null) {
            System.out.println("Are you sure you want to remove all players? (y/n)");
            if (keyboard.nextLine().equals("y")) {
                playerlist = new NimPlayer[100];
            }
            return;
        }
        int index = checkExist(username);
        if (playerlist[index] == null) {
            System.out.println("The player does not exist.");
            return;
        }
        if (index == 100 || !(playerlist[index].username.equals(username))) {
            System.out.println("The player does not exist.");
        } else {
            for (int i = index; i < 99; i++) {
                if (playerlist[i + 1] != null) {
                    playerlist[i] = playerlist[i + 1];
                } else {
                    playerlist[i] = null;
                    return;
                }
                playerlist[99] = null;
            }
        }
    }

    //    Update the player's first name and last name if the player is exist.
    private void editPlayer(String playerinfo) {
        String[] argu = playerinfo.split(",");
        String username = argu[0];
        String firstname = argu[1];
        String lastname = argu[2];

        int index = checkExist(username);
        if (playerlist[index] == null) {
            System.out.println("The player does not exist.");
            return;
        }
        if (index == 100 || !(playerlist[index].username.equals(username))) {
            System.out.println("The player does not exist.");
        } else {
            NimPlayer player = playerlist[index];
            player.edit(firstname, lastname);
        }
    }

    //Check the commend first.
    //Then, reset all statistics or reset the aim player's statistics.
    private void resetStats(String playerinfo) {
        String username = playerinfo;

        if (username == null) {
            System.out.println("Are you sure you want to reset all player statistics? (y/n)");
            if (keyboard.nextLine().equals("y")) {
                for (int i = 0; i < 100; i++) {
                    NimPlayer player = playerlist[i];
                    if (player != null) {
                        player.reset();
                    }
                }
            }
            return;
        }

        int index = checkExist(username);
        if (playerlist[index] == null) {
            System.out.println("The player does not exist.");
            return;
        }
        if (index == 100 || !(playerlist[index].username.equals(username))) {
            System.out.println("The player does not exist.");
        } else {
            NimPlayer player = playerlist[index];
            player.reset();
        }
    }

    //Check the commend first.
    //Then, display all player's information or display the aim player's information.
    private void displayPlayer(String playerinfo) {
        String username = playerinfo;

        if (username == null) {
            for (int i = 0; i < 100; i++) {
                NimPlayer player = playerlist[i];
                if (playerlist[i] != null) {
                    System.out.println(player.printPlayer());
                } else {
                    return;
                }
            }
        }

        int index = checkExist(username);
        if (index == 100 || playerlist[index] == null) {
            System.out.println("The player does not exist.");
        } else {
            NimPlayer player = playerlist[index];
            System.out.println(player.printPlayer());
        }
    }

    //Rank the players by winning ratio.
    //Display the top 10 players in descending order or ascending order.
    private void rankings(String order) {
        if (order == null) order = "desc";
        int count = 0;

        //Initialize rank list
        int[] ranklist = new int[10];
        for (int i = 0; i < ranklist.length; i++) {
            ranklist[i] = -1;
        }

        for (int i = 1; i < 11; i++) {
            for (int j = 0; j < 100; j++) {
                int rank = 1;
                NimPlayer playerj = playerlist[j];
                for (int k = 0; k < 100; k++) {
                    NimPlayer playerk = playerlist[k];
                    if (playerj != null && playerk != null) {
                        if (playerj.getRatio() < playerk.getRatio())
                            rank++;
                    }
                }
                if (rank == i && playerj != null && count < 10) {
                    ranklist[count] = j;
                    count++;
                }
            }
        }

        if (order.equals("asc")) {
            for (int i = ranklist.length - 1; i >= 0; i--) {
                if (ranklist[i] != -1) {
                    NimPlayer player = playerlist[ranklist[i]];
                    System.out.println(player.printRankings());
                }
            }
        } else {
            for (int i = 0; i < ranklist.length; i++) {
                if (ranklist[i] != -1) {
                    NimPlayer player = playerlist[ranklist[i]];
                    System.out.println(player.printRankings());
                }
            }
        }
    }

    //Start a game, get the game result and update the players information
    private void startGame(String gameinfo) {
        String[] argu = gameinfo.split(",");
        int initialstones = Integer.parseInt(argu[0]);
        int upperbound = Integer.parseInt(argu[1]);
        String username1 = argu[2];
        String username2 = argu[3];
        int index1 = checkExist(username1);
        int index2 = checkExist(username2);
        NimPlayer player1 = playerlist[index1];
        NimPlayer player2 = playerlist[index2];

        if (player1 == null || player2 == null) {
            System.out.println("One of the players does not exist.");
            return;
        } else if (!player1.getUsername().equals(username1) || !player2.getUsername().equals(username2)) {
            System.out.println("One of the players does not exist.");
        } else {
            NimGame game = new NimGame(initialstones, upperbound, player1, player2);
            game.getWinner().Win();
            game.getLoser().Lose();
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Nim");
        keyboard = new Scanner(System.in);
        //initialize the NimPlayer array
        playerlist = new NimPlayer[100];
        Nimsys sys = new Nimsys();

        while (true) {
            sys.identifyCommand(sys.scanCommand());
        }
    }
}
