/**
 * Created by Shen YI(844373) syi2 on 2017/3/26.
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class Nimsys {
    static Scanner keyboard;
    static NimPlayer[] playerlist = new NimPlayer[100];

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
            try {
                String[] argument = commandsplit[1].split(",");
                addPlayer(argument[0], argument[1], argument[2]);
            } catch (ArrayIndexOutOfBoundsException e) {
                invalidArgument();
            }
        } else if (commandsplit[0].equals("addaiplayer")) {
            try {
                String[] argument = commandsplit[1].split(",");
                addAIPlayer(argument[0], argument[1], argument[2]);
            } catch (ArrayIndexOutOfBoundsException e) {
                invalidArgument();
            }
        } else if (commandsplit[0].equals("removeplayer")) {
            try {
                removePlayer(commandsplit[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                removePlayer();
            }
        } else if (commandsplit[0].equals("editplayer")) {
            try {
                String[] argument = commandsplit[1].split(",");
                editPlayer(argument[0], argument[1], argument[2]);
            } catch (ArrayIndexOutOfBoundsException e) {
                invalidArgument();
            }
        } else if (commandsplit[0].equals("resetstats")) {
            try {
                resetStats(commandsplit[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                resetStats();
            }
        } else if (commandsplit[0].equals("displayplayer")) {
            try {
                displayPlayer(commandsplit[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                displayPlayer();
            }
        } else if (commandsplit[0].equals("rankings")) {
            try {
                rankings(commandsplit[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                rankings("desc");
            }
        } else if (commandsplit[0].equals("startgame")) {
            try{
                String[] argument = commandsplit[1].split(",");
                try{
                    startGame(argument[0], argument[1], argument[2], argument[3]);
                }catch (Exception e){
                    invalidArgument();
                }
            }catch (Exception e){
                invalidArgument();
            }
        } else if (commandsplit[0].equals("startadvancedgame")) {
            try{
                String[] argument = commandsplit[1].split(",");
                try{
                    startAdvancedgame(argument[0], argument[1], argument[2]);
                }catch (Exception e){
                    invalidArgument();
                }
            }catch (Exception e){
                invalidArgument();
            }
        } else if (commandsplit[0].equals("exit")) {
            write();
            System.out.println();
            System.exit(0);
        } else {
            invalidCommand(commandsplit[0]);
        }
    }

    private void invalidCommand(String command) {
        System.out.println("'"+command + "' is not a valid command.");
    }

    private void invalidArgument() {
        System.out.println("Incorrect number of arguments supplied to command.");
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
    private void addPlayer(String username, String lastname, String firstname) {
//        String[] argu = playerinfo.split(",");
//        String username = argu[0];

        int index = checkExist(username);
        if (index == 100) {
            System.out.println("full");
        } else if (playerlist[index] == null) {
            playerlist[index] = new NimHumanPlayer(username, lastname, firstname, false);
        } else if (playerlist[index].username.equals(username)) {
            System.out.println("The player already exists.");
        } else {
            for (int i = 99; i > index; i--) {
                if (playerlist[i - 1] != null) {
                    playerlist[i] = playerlist[i - 1];
                }
            }
            playerlist[index] = new NimHumanPlayer(username, lastname, firstname, false);
        }
    }

    private void addAIPlayer(String username, String lastname, String firstname) {

        int index = checkExist(username);
        if (index == 100) {
            System.out.println("full");
        } else if (playerlist[index] == null) {
            playerlist[index] = new NimAIPlayer(username, lastname, firstname, true);
        } else if (playerlist[index].username.equals(username)) {
            System.out.println("The player already exists.");
        } else {
            for (int i = 99; i > index; i--) {
                if (playerlist[i - 1] != null) {
                    playerlist[i] = playerlist[i - 1];
                }
            }
            playerlist[index] = new NimAIPlayer(username, lastname, firstname, true);
        }
    }

    //    Check the commend first.
//    Then, remove all players or remove the aim player if exist.
    private void removePlayer(String username) {

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

    private void removePlayer() {
        System.out.println("Are you sure you want to remove all players? (y/n)");
        if (keyboard.nextLine().equals("y")) {
            playerlist = new NimPlayer[100];
        }
    }

    //    Update the player's first name and last name if the player is exist.
    private void editPlayer(String username, String lastname, String firstname) {

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
    private void resetStats(String username) {

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

    private void resetStats() {
        System.out.println("Are you sure you want to reset all player statistics? (y/n)");
        if (keyboard.nextLine().equals("y")) {
            for (int i = 0; i < 100; i++) {
                NimPlayer player = playerlist[i];
                if (player != null) {
                    player.reset();
                }
            }
        }
    }

    //Check the commend first.
    //Then, display all player's information or display the aim player's information.
    private void displayPlayer(String username) {

        int index = checkExist(username);
        if (index == 100 || playerlist[index] == null||!playerlist[index].username.equals(username)) {
            System.out.println("The player does not exist.");
        } else {
            NimPlayer player = playerlist[index];
            System.out.println(player.printPlayer());
        }
    }

    private void displayPlayer() {
        for (int i = 0; i < 100; i++) {
            NimPlayer player = playerlist[i];
            if (playerlist[i] != null) {
                System.out.println(player.printPlayer());
            } else {
                return;
            }
        }
    }

    //Rank the players by winning ratio.
    //Display the top 10 players in descending order or ascending order.
    private void rankings(String order) {
//        if (order == null) order = "desc";
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
    private void startGame(String stones, String bound, String username1, String username2) {
        int initialstones = Integer.parseInt(stones);
        int upperbound = Integer.parseInt(bound);
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
            game.playingGame(player1,player2);
            game.getWinner().Win();
            game.getLoser().Lose();
        }
    }

    private void startAdvancedgame(String stones,String username1, String username2){
        int initialstones = Integer.parseInt(stones);
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
            AdvancedGame game = new AdvancedGame(initialstones, player1, player2);
            game.playingGame(player1,player2);
            game.getWinner().Win();
            game.getLoser().Lose();
        }
    }

    private static boolean checkFile(File file) {
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    private void read() {
//        System.out.println("Now let's reopen the file and display the array.");

        String[] b = null;

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("players.dat"));
            b = (String[]) inputStream.readObject();
            inputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file players.dat.");
            System.exit(0);
        } catch (ClassNotFoundException e) {
            System.out.println("Problems with file input.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Problems with file input.");
            System.exit(0);
        }

//        System.out.println("The following array elements were read from the file:");
        int j;
        for (j = 0; j < b.length; j++) {
            if (b[j] != null) {
                String[] playerinfo = b[j].split(",");
                try {
                    if (playerinfo[5].equals("true")){
                        addAIPlayer(playerinfo[0], playerinfo[1], playerinfo[2]);
                        NimPlayer player = playerlist[checkExist(playerinfo[0])];
                        try {
                            int game = Integer.valueOf(playerinfo[3]);
                            int win = Integer.valueOf(playerinfo[4]);
                            player.updateGame(game);
                            player.updateWin(win);

                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }else {
                        addPlayer(playerinfo[0], playerinfo[1], playerinfo[2]);
                        NimPlayer player = playerlist[checkExist(playerinfo[0])];
                        try {
                            int game = Integer.valueOf(playerinfo[3]);
                            int win = Integer.valueOf(playerinfo[4]);
                            player.updateGame(game);
                            player.updateWin(win);

                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    System.out.println("Problems with file input.");
                }
            }
        }
    }

    private static void write() {

        String[] a = new String[100];

        for (int i = 0; i < 100; i++) {
            NimPlayer player = playerlist[i];

            if (playerlist[i] != null) {
                a[i] = player.getUpdatefile();
            }
        }
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("players.dat"));

            outputStream.writeObject(a);
            outputStream.close();
        } catch (IOException e) {
            System.out.println("Error writing to file.");
            System.exit(0);
        }
    }


    public static void main(String[] args) {
        System.out.println("Welcome to Nim");
        keyboard = new Scanner(System.in);


        //initialize the NimPlayer array
        Nimsys sys = new Nimsys();
        String fileName = "players.dat";
        File fileObject = new File(fileName);
        if (checkFile(fileObject)) {
            sys.read();
        }

        while (true) {
            sys.identifyCommand(sys.scanCommand());
        }
    }
}
