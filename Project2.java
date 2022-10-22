import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Project2 {

    private ArrayList<String> users;
    private ArrayList<String> pwHashes;



    public static void main(String[] args){
        try{
            Project2 pwc = new Project2();
            pwc.runLoop();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }


    // Creates an object with 2 arrays, one of users, and one of hashed/salted passwords
    // It then reads in all values from the given file "Project2PW.txt" and adds the parts to the correct arrays
    // If the password file does not already exist, it creates one
    // Throws an exception if it could not create a file
    public Project2() throws FileNotFoundException{
        // Initialization
        Scanner fileScn = null;
        boolean fileLoaded = false;
        // Loop tries to read the file and if it fails, it tried to create the file.
        // Throws an exception if creation failed
        while (!fileLoaded) {
            try {
                // Reads in the file
                fileScn = new Scanner(new File("Project2PW.txt"));
                fileLoaded = true;
            } catch (Exception e) {
                // Creates empty password file if non-existent
                System.out.println("Creating File");
                try {
                    PrintWriter newOut = new PrintWriter("Project2PW.txt");
                    newOut.flush();
                    newOut.close();
                } catch (Exception epw) {
                    throw new FileNotFoundException();
                }
            }
        }
        // Instantiates arrays
        users = new ArrayList<>();
        pwHashes = new ArrayList<>();

        // Fills the arrays from the password file
        while(fileScn.hasNextLine()){
            String ln = fileScn.nextLine();
            String[] splitLn = ln.split(":");
            if(splitLn.length == 2) {
                users.add(splitLn[0]);
                pwHashes.add(splitLn[1]);
            }
        }
        fileScn.close();
    }

    // This function runs a loop which is the bulk of the program
    // Each loop the user is promted for input which is then handled
    // Once the user enters "quit", the loop ends and the current users and passwords are output to a file
    // IF THE PROGRAM IS CANCELLED IT WILL NOT BE OUTPUT TO A FILE
    public void runLoop() throws FileNotFoundException {
        try {
            //Initializations
            Scanner scn = new Scanner(System.in);
            PrintWriter out = new PrintWriter("Project2PW.txt");

            while(true){

                // Gets input
                System.out.print(": ");
                String input = scn.nextLine();

                // Handles quiting and saving data
                if(input.toLowerCase().equals("quit")){
                    printToFile(out);
                    out.flush();
                    out.close();
                    break;
                }
                // Handles the "help" command which prints all valid commands
                if(input.length() >= 4 && input.substring(0,4).toLowerCase().equals("help")){
                    printCommands();
                }
                // Handles the "add-user" command
                // Checks input for invalid chars (like ':') and invalid command format
                // Splits command into pieces for addUser
                // Prints "Success" or "Error" based on outcome
                else if(input.length() >= 8 && input.substring(0,8).toLowerCase().equals("add-user")){
                    if(input.contains(":")){
                        System.out.println("Invalid username or password, : may not be used");
                    }
                    else {
                        String[] splitInput = input.split(" ");
                        if (splitInput.length != 3){
                            System.out.println("Invalid Syntax");
                        }
                        else {
                            String user = splitInput[1];
                            String pw = splitInput[2];
                            if(addUser(user, pw)){
                                System.out.println("Success");
                            }
                            else{
                                System.out.println("Error");
                            }
                        }
                    }
                }

                // Handles the "check-password" command
                // Checks for invalid command format
                // Splits command into pieces for checkPassword
                // Prints "Success" or "Incorrect Password, please try again" based on outcome
                else if(input.length() >= 14 && input.substring(0,14).toLowerCase().equals("check-password")){

                    String[] splitInput = input.split(" ");
                    if (splitInput.length != 3){
                        System.out.println("Invalid Syntax");
                    }
                    else {
                        String user = splitInput[1];
                        String pw = splitInput[2];
                        if(checkPassword(user, pw)){
                            System.out.println("Success");
                        }
                        else{
                            System.out.println("Incorrect Password, please try again");
                        }
                    }
                }

                // Handles the "remove-user" command
                // Checks for invalid command format
                // Splits the command into pieces for removeUser
                // Prints "Successfully removed" or "Error, please try again" based on outcome
                else if(input.length() >= 11 && input.substring(0,11).toLowerCase().equals("remove-user")){
                    String[] splitInput = input.split(" ");
                    if (splitInput.length != 2){
                        System.out.println("Invalid Syntax");
                    }
                    else {
                        if(removeUser(splitInput[1])){
                            System.out.println("Successfully removed");
                        }
                        else{
                            System.out.println("Error, please try again");
                        }
                    }
                }

                // Prints the arrays in the format which they are saved in the password file
                else if(input.length() >= 5 && input.substring(0,5).toLowerCase().equals("print")){
                    print();
                }

                // Prints an error message if nothing else triggered
                else{
                    System.out.println("Unknown command");
                }
            }
        } catch(Exception e){
            throw e;
        }

    }


    public boolean addUser(String user, String pw){

        // Temporary for testing
        users.add(user);
        pwHashes.add(pw);
        return true;
    }

    public boolean checkPassword(String user, String pw){

        //Temporary for testing
        int index = users.indexOf(user);
        if(index != -1){
            return pwHashes.get(index).equals(pw);
        }
        return false;
    }

    // Takes the string user and removes it from the users array and it's correlating password from pwHashes
    // Returns true if successful, false if not
    public boolean removeUser(String user){
        if(users.size() == pwHashes.size()) {
            int index = users.indexOf(user);
            if(index != -1) {
                users.remove(index);
                pwHashes.remove(index);
                return true;
            }
        }
        return false;
    }

    // Prints the two arrays to System.Out in the format they are printed to the file
    public void print(){
        if(users.size() == pwHashes.size()) {
            for (int i = 0; i < users.size(); i++)
                System.out.println(users.get(i) + ":" + pwHashes.get(i));
        }
    }

    // Prints the two arrays to the file
    public void printToFile(PrintWriter out){
        if(users.size() == pwHashes.size()) {
            for (int i = 0; i < users.size(); i++)
                out.print(users.get(i) + ":" + pwHashes.get(i) + "\n");
        }
    }

    // Prints help commands
    public void printCommands(){
        System.out.println("add-user \"username\" \"password\"");
        System.out.println("check-password \"username\" \"password\"");
        System.out.println("remove-user \"username\"");
        System.out.println("print");
        System.out.println("quit");
        System.out.println("help");
    }
}
