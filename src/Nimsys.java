/**
 * Created by Shen YI(844373) syi2 on 2017/3/26.
 */

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

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
            String[] argument = commandsplit[1].split(",");
            if (argument.length == 3){
                addPlayer(argument[0],argument[1],argument[2]);
            }else invalidArgument();
        } else if (commandsplit[0].equals("removeplayer")) {
            if (commandsplit.length == 1) {
                removePlayer(null);
            } else {
                removePlayer(commandsplit[1]);
            }
        } else if (commandsplit[0].equals("editplayer")) {
            if (commandsplit.length == 2){
                editPlayer(commandsplit[1]);
            }else {
                invalidArgument();
            }
        } else if (commandsplit[0].equals("resetstats")) {
            if (commandsplit.length == 1) {
                resetStats(null);
            } else {
                resetStats(commandsplit[1]);
            }
        } else if (commandsplit[0].equals("displayplayer")) {
            if (commandsplit.length == 1) {
                displayPlayer(null);
            } else {
                displayPlayer(commandsplit[1]);
            }
        } else if (commandsplit[0].equals("rankings")) {
            if (commandsplit.length == 1) {
                rankings(null);
            } else {
                if (commandsplit[1]=="desc"||commandsplit[1]=="asc"){
                    rankings(commandsplit[1]);
                }else {
                    invalidArgument();
                }
            }
        } else if (commandsplit[0].equals("startgame")) {
            if (commandsplit.length==2){
                String[] argument = commandsplit[1].split(",");
                if (argument.length == 4){
                    startGame(argument[0],argument[1],argument[2],argument[3]);
                }else invalidArgument();
            }
        } else if (commandsplit[0].equals("exit")) {
            updateFile();
            System.out.println();
            System.exit(0);
        } else {
            invalidCommand(commandsplit[0]);
        }
    }

    private void invalidCommand(String command){
        System.out.println(command+" is not a valid command");
    }

    private void invalidArgument(){
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
    private void addPlayer(String username,String lastname, String firstname) {
//        String[] argu = playerinfo.split(",");
//        String username = argu[0];

        int index = checkExist(username);
        if (index == 100) {
            System.out.println("full");
        } else if (playerlist[index] == null) {
            playerlist[index] = new NimPlayer(username, lastname, firstname);
        } else if (playerlist[index].username.equals(username)) {
            System.out.println("The player already exists.");
        } else {
            for (int i = 99; i > index; i--) {
                if (playerlist[i - 1] != null) {
                    playerlist[i] = playerlist[i - 1];
                }
            }
            playerlist[index] = new NimPlayer(username, lastname, firstname);
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
    private void startGame(String stones,String bound,String username1,String username2) {
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
            game.getWinner().Win();
            game.getLoser().Lose();
        }
    }

    private static void checkFile(File file,String fileNmae){
        if (!file.exists()){
            creat(fileNmae);
        }
    }
    private static void creat(String fileName){
        PrintWriter outputStream = null;
        try
        {
            outputStream = new PrintWriter(new FileOutputStream(fileName));
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Error opening the file " + fileName);
            System.exit(0);
        }
        outputStream.close( );
    }

//    private static void write(){
//        NimPlayer a = playerlist[0];
//        String username = "es";
//        String lastname = "shen";
//        String firstname = "yi";
//        a = new NimPlayer(username,lastname,firstname);
//
//        try
//        {
//            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("players.dat"));
//
//            outputStream.writeObject(a);
//            outputStream.close( );
//        }
//        catch(IOException e)
//        {
//            System.out.println("Error writing to file.");
//            System.exit(0);
//        }
//
//        System.out.println(
//                "Array written to file players.dat.");
//
//        System.out.println(
//                "Now let's reopen the file and display the array.");
//
//        NimPlayer[] b = null;
//
//        try
//        {
//            ObjectInputStream inputStream =
//                    new ObjectInputStream(new FileInputStream("players.dat"));
//            b = (NimPlayer [])inputStream.readObject( );
//            inputStream.close( );
//        }
//        catch(FileNotFoundException e)
//        {
//            System.out.println("Cannot find file players.dat.");
//            System.exit(0);
//        }
//        catch(ClassNotFoundException e)
//        {
//            System.out.println("Problems with file input.");
//            System.exit(0);
//        }
//        catch(IOException e)
//        {
//            System.out.println("Problems with file input.");
//            System.exit(0);
//        }
//
//        System.out.println(
//                "The following array elements were read from the file:");
//        int i;
//        for (i = 0; i < b.length; i++)
//            System.out.println(b[i]);
//
//        System.out.println("End of program.");
//    }

    private static void updateFile(){
        for (int i = 0; i < 100; i++) {
            NimPlayer player = playerlist[i];
            if (playerlist[i] != null) {
                String[] file = player.getUpdatefile();
                try
                    {
                        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("players.dat"));

                        outputStream.writeObject(file);
                        outputStream.close( );
                    }
                    catch(IOException e)
                    {
                        System.out.println("Error writing to file.");
                        System.exit(0);
                    }
            } else {
                return;
            }
        }
    }
//    public static void readTxtFile(String filePath){
//        try {
//            String encoding="UTF-16";
//            File file=new File(filePath);
//            if(file.isFile() && file.exists()){ //判断文件是否存在
//                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
//                BufferedReader bufferedReader = new BufferedReader(read);
//                String lineTxt = null;
//                while((lineTxt = bufferedReader.readLine()) != null){
//                    System.out.println(lineTxt);
//                }
//                read.close();
//            }else{
//                System.out.println("找不到指定的文件");
//            }
//        } catch (Exception e) {
//            System.out.println("读取文件内容出错");
//            e.printStackTrace();
//        }
//    }
    private static void inputFlie(String filePath){
        try{
//            String encoding = "UTF-8";
            File file= new File(filePath);
            if (file.isFile() && file.exists()){
                ObjectInputStream read = new ObjectInputStream(new FileInputStream(file));
//                BufferedReader bufferedReader = new BufferedReader(read);
//                int lineTxt = null;
                while(read.read() != -1){
                    System.out.println(read.read());
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (FileNotFoundException e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Nim");
        keyboard = new Scanner(System.in);
//        String fileName = "players.dat";
//        File fileObject = new File(fileName);
//        checkFile(fileObject,fileName);



        //initialize the NimPlayer array
        playerlist = new NimPlayer[100];
        Nimsys sys = new Nimsys();

        String fileName = "players.dat";
        File file = new File(fileName);
        inputFlie(fileName);
//        readTxtFile(fileName);

//        write();

        while (true) {
            sys.identifyCommand(sys.scanCommand());
        }
    }
}
