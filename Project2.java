public class Project2 {

    private ArrayList<String> users;
    private ArrayList<String> pwHashes;


    public static void main(String[] args){
        try{
            Project2 pwc = new Project2();
        } catch (Exception e){
            System.out.println("File Error");
        }
    }

    public Project2() throws FileNotFoundException{
        Scanner scn = new Scanner(System.in);
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

        while(fileScn.hasNextLine()){
            fileScn.nextLine();
            
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
                out.close();
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
