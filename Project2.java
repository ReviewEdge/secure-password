import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Project2 {

    ArrayList<String> pwFile;



    public static void main(String[] args) throws FileNotFoundException {

        Scanner scn = new Scanner(System.in);

        boolean fileLoaded = false;
        while (!fileLoaded) {
            try {
                Scanner fileScn = new Scanner(new File("Project2PW.txt"));
                fileLoaded = true;
            } catch (Exception e) {
                System.out.println("Creating File");
                try {
                    PrintWriter newOut = new PrintWriter("Project2PW.txt");
                    newOut.flush();
                    newOut.close();
                } catch(Exception epw){
                    throw new FileNotFoundException();
                }
            }
        }

        PrintWriter out;
        try {
            out = new PrintWriter("Project2PW.txt");
        } catch(Exception e){
            throw new FileNotFoundException();
        }

        while(true){
            System.out.println(": ");
            String input = scn.nextLine();
            if(input.toLowerCase().equals("quit")){
                out.flush();


                break;
            }



        }
    }

    public boolean addUser(){

        return true;
    }

    public boolean checkUser(){

        return true;
    }

    public boolean removeUser(){

        return true;
    }

    public void print(){


    }







}
