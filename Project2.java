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

    public Project2() throws FileNotFoundException{

        Scanner fileScn = null;
        boolean fileLoaded = false;
        while (!fileLoaded) {
            try {
                fileScn = new Scanner(new File("Project2PW.txt"));
                fileLoaded = true;
            } catch (Exception e) {
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
        users = new ArrayList<>();
        pwHashes = new ArrayList<>();

        while(fileScn.hasNextLine()){
            String ln = fileScn.nextLine();
            String[] splitLn = ln.split(":");
            users.add(splitLn[0]);
            pwHashes.add(splitLn[1]);

        }
        fileScn.close();
    }

    public void runLoop() throws FileNotFoundException {
        try {
            Scanner scn = new Scanner(System.in);
            PrintWriter out = new PrintWriter("Project2PW.txt");


            while(true){
                System.out.print(": ");
                String input = scn.nextLine();
                if(input.toLowerCase().equals("quit")){
                    printToFile(out);
                    out.flush();
                    out.close();
                    break;
                }
                if(input.length() >= 4 && input.substring(0,4).toLowerCase().equals("help")){
                    printCommands();
                }
                else if(input.length() >= 8 && input.substring(0,8).toLowerCase().equals("add-user")){
                    if(input.contains(":")){
                        System.out.println("Invalid username or password, : may not be used");
                    }
                    else {
                        String[] splitInput = input.split(" ");
                        if (splitInput.length > 3){
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
                else if(input.length() >= 14 && input.substring(0,14).toLowerCase().equals("check-password")){
                    if(input.contains(":")){
                        System.out.println("Invalid username or password, : may not be used");
                    }
                    else {
                        String[] splitInput = input.split(" ");
                        if (splitInput.length > 3){
                            System.out.println("Invalid Syntax");
                        }
                        else {
                            String user = splitInput[1];
                            String pw = splitInput[2];
                            if(checkUser(user, pw)){
                                System.out.println("Success");
                            }
                            else{
                                System.out.println("Incorrect Password, please try again");
                            }
                        }
                    }
                }
                else if(input.length() >= 11 && input.substring(0,11).toLowerCase().equals("remove-user")){
                    String[] splitInput = input.split(" ");
                    if (splitInput.length > 2){
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
                else if(input.length() >= 5 && input.substring(0,5).toLowerCase().equals("print")){
                    print();
                }
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

    public boolean checkUser(String user, String pw){

        //Temporary for testing
        int index = users.indexOf(user);
        if(index != -1){
            return pwHashes.get(index).equals(pw);
        }
        return false;
    }

    public boolean removeUser(String user){
        if(users.size() == pwHashes.size()) {
            int i = users.indexOf(user);
            users.remove(i);
            pwHashes.remove(i);
            return true;
        }
        return false;
    }

    public void print(){
        if(users.size() == pwHashes.size()) {
            for (int i = 0; i < users.size(); i++)
                System.out.println(users.get(i) + ":" + pwHashes.get(i));
        }
    }

    public void printToFile(PrintWriter out){

    }

    public void printCommands(){
        System.out.println("add-user \"username\" \"password\"");
        System.out.println("check-password \"username\" \"password\"");
        System.out.println("remove-user \"username\"");
        System.out.println("print");
        System.out.println("quit");
        System.out.println("help");
    }
}
