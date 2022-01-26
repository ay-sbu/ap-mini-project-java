
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



class User{
    String username ;
    String password ;
    int score = 0;

    public User() {
        username = "username";
        password = "password";
    }

    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    String setUsername() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}




public class Final {
    public static void main(String[] args) throws InterruptedException, IOException {
        cls();
        Scanner in = new Scanner(System.in);
        
        
        User[]user = new User[50];
        int usercount = 0;
        int whichuser = 0;
        
        for (int i = 0; i < 50; i++) {
            user[i]=new User("username", "password");
        }
        
        
        //returning data
        try {
            try (FileReader reader = new FileReader("gamedata.txt")) {
                BufferedReader bufferedReader = new BufferedReader(reader);
                
                String line;
                int saveCounter = 0;
                
                while ((line = bufferedReader.readLine()) != null) {
                    user[saveCounter].setUsername(line.substring(line.indexOf(' ')+3, line.indexOf(',')-1)) ;
                    user[saveCounter].setPassword(line.substring(line.indexOf(',')+13,line.lastIndexOf('s')-3));
                    user[saveCounter].setScore(Integer.parseInt(line.substring(line.lastIndexOf(':')+2,line.lastIndexOf('.'))));
                    saveCounter++;
                }
                usercount = saveCounter;
            }
 
        } catch (IOException e) {
        }
         
        
            
        while (true) {            
            System.out.println("\033[41m"+"\n Hangman Game \n" + "\033[0m");
            System.out.println("\033[1;32m"+" 1."+ "\033[0m"+"sign up");
            System.out.println("\033[1;32m"+" 2."+ "\033[0m"+"log in");
            System.out.println("\033[1;32m"+" 3."+ "\033[0m"+"exit");

            System.out.print("\033[1;36m"+"\n Choose : "+ "\033[0m");
            char ch = in.next().charAt(0);

            if (ch=='3') {
                exit(user, usercount);
            }
            
            if (ch=='1') {//1.sign up

                System.out.print("\033[1;33m"+"\n Enter username : "+"\033[0m");
                String nam = in.nextLine();
                nam = in.nextLine();

                int us;
                for (us = 0; us < usercount; us++) {
                    if (nam.equals(user[us].getUsername())) {
                        break;
                    }
                }
                
                if (us!=usercount) {
                    
                    System.out.println("\033[1;31m"+"\n Usermae entered already! try again!"+"\033[0m");
                    TimeUnit.SECONDS.sleep(2);
                    
                }else{
                    
                    Console console = System.console();
                    if (console == null) {
                        System.out.println(" console is not supported");
                        System.exit(-1);
                    }
                
                    char[] password = console.readPassword("\033[1;33m"+" Enter Password : "+"\033[0m","*");
                    
                    String pass = "";
                    for (int i = 0; i < password.length; i++) {
                        pass+=password[i];
                    }
                    
                    if (!checkPassword(pass)) {
                        
                        System.out.println("\033[1;31m"+"\n increas your password strength"+"\033[0m");
                        TimeUnit.SECONDS.sleep(7);
                    
                    }else{
                        
                        User user1 = new User(nam, pass);
                        user[usercount]=user1;
                        usercount++;

                        System.out.println("\033[0;32m"+"\n Successfully signed up."+"\033[0m");
                        TimeUnit.SECONDS.sleep(1);
                        
                    }
                    
                    
                        
                    
                }
                
                
                
            }else if (ch=='2') {//2.log in

                System.out.print("\033[1;33m"+"\n Enter username : "+"\033[0m");
                String nam = in.nextLine();
                nam = in.nextLine();

                Console console = System.console();
                    if (console == null) {
                        System.out.println("console is not supported");
                        System.exit(-1);
                    }
                    
                    char[] password = console.readPassword("\033[1;33m"+" Enter Password : "+"\033[0m","*");
                    
                    String pass = "";
                    for (int i = 0; i < password.length; i++) {
                        pass+=password[i];
                    }
                
                int k;
                for (k = 0; k < usercount; k++) {
                    if (user[k].getUsername().equals(nam)) {
                        whichuser = k;
                        break;
                    }
                }
                if (k==usercount) {

                        System.out.println("\033[1;31m"+"\n Invalid username!"+"\033[0m");
                        TimeUnit.SECONDS.sleep(2);

                }else{
                    
                    if (!user[whichuser].getPassword().equals(pass)) {
                        
                        System.out.println("\033[1;31m"+"Invalid Password!"+"\033[0m");
                        TimeUnit.SECONDS.sleep(2);
                        
                    }else{
                        cls();
                        while (true) {   
                            System.out.println("\033[41m"+"\n Player : " + user[whichuser].getUsername() + " , score : " + user[whichuser].getScore() + " \n"+ "\033[0m");
                            System.out.println("\033[1;32m"+" 1."+ "\033[0m"+"start game");
                            System.out.println("\033[1;32m"+" 2."+ "\033[0m"+"show leaderboard");
                            System.out.println("\033[1;32m"+" 3."+ "\033[0m"+"log out");
                            System.out.println("\033[1;32m"+" 4."+ "\033[0m"+"exit");
                            
                            System.out.print("\033[1;36m"+"\n Choose : "+ "\033[0m");
                            char ch2 = in.next().charAt(0);
                            
                            if (ch2=='4') {
                                
                                exit(user, usercount);
                            }

                            if (ch2=='1') {//1.start game


                                wordgame(user[whichuser]);
                                saveData(user, usercount);

                            }else if (ch2=='2') {//2.ledearboard
                                cls();
                                System.out.println("\033[41m"+"\n Player : " + user[whichuser].getUsername() + " , score : " + user[whichuser].getScore() + " \n"+ "\033[0m");

                                System.out.println("\033[44m"+" SCORE BOARD "+ "\033[0m");

                                
                                System.out.println("");
                                for (int i=usercount-1; i>=0; i--) {
                                    System.out.print(" "+(usercount-i)+" "+user[i].getUsername());
                                    for (int j = 0; j < 15-user[i].getUsername().length(); j++) {
                                        System.out.print("-");
                                    }
                                    System.out.println(user[i].getScore());
                                }

                                System.out.print("\n back...");
                                char chr = in.next().charAt(0);

                            }else if (ch2=='3') {//3.log out
                                
                                break;
                              
                            } else {

                                System.out.println("\033[1;31m"+"\n Invalid input"+"\033[0m");
                                TimeUnit.SECONDS.sleep(2);

                            }

                            cls();

                        }
                        
                    }

                }
                    
                
                
            }else{
                System.out.println("\033[1;31m"+"\n Invalid input"+"\033[0m");
                TimeUnit.SECONDS.sleep(2);
            }
            
            sortUsers(user, usercount);
            saveData(user, usercount);
            
            
            //TimeUnit.SECONDS.sleep(10);
            
            cls();
        }
        
    }
    
    public static void saveData(User[]user,int usercount){
        //saving data
            String textFileSave="";
                for (int i = 0; i < usercount; i++) {
                    //System.out.print("name : " + data1.name[i] + " , ");
                    textFileSave+="name : " + user[i].getUsername() + " , ";
                    
                    //System.out.print("password : " + data1.passwd[i] + " , ");
                    textFileSave+="password : " + user[i].getPassword() + " , ";
                    
                    //System.out.println("score : " + data1.score[i] + "      ");
                    textFileSave+="score : " + Integer.toString(user[i].score) + ".\n";
                }
                
            
                //creating file    
                try {
                    File myObj = new File("gamedata.txt");
                    if (myObj.createNewFile()) {
                      System.out.println("File created: " + myObj.getName());
                    } else {
                      System.out.println("File already exists.");
                    }
                  } catch (IOException e) {
                    System.out.println("An error occurred.");
                  }

                //file writing
                try {
                try (FileWriter myWriter = new FileWriter("gamedata.txt")) {
                    myWriter.write(textFileSave);
                }
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                }
    }
    
    static void wordgame(User user) throws InterruptedException{
        Scanner in = new Scanner(System.in);
        cls();
        
        String[]words = {"tehran", "pizza", "banana", "new york",
                    "advanced programming", "michael jordan","lionel messi","apple", 
                    "macaroni", "university","intel", "kitten",
                    "python", "java","data structures", "algorithm",
                    "assembly", "basketball","hockey", "leader",
                    "javascript","toronto", "united states of america", "psychology",
                    "chemistry", "breaking bad", "physics","abstract classes", 
                    "linux kernel", "january", "march","time travel",
                    "twitter", "instagram", "dog breeds", "strawberry",
                    "snow", "game of thrones", "batman", "ronaldo",
                    "soccer", "hamburger", "italy", "greece",
                    "albert einstein", "hangman", "clubhouse", "call of duty",
                    "science", "theory of languages and automata"};
        
        int i =(int)(Math.random()*50);
        String selected = words[i];
        //System.out.println("words[" + i + "] = " + selected);
        
        boolean winner = false;
        
        char[]partGussed = new char[selected.length()];
        
        for (int j = 0; j < partGussed.length; j++) {
            if (selected.charAt(j)==' ') {
                partGussed[j]=' ';
            }else{
                partGussed[j]='-';
            }
            
        }
        
        
        char[]charsInputed;
        if (selected.length()<=9) {
            charsInputed = new char[7];
        }else{
            charsInputed = new char[14];
        }
    
        
        
        int chandbar=0;
        boolean help = true;//1help exist when help is true//
        
        while (!winner) {   
            boolean inputis = false;
            
            System.out.println("\033[41m"+"\n Player : " + user.getUsername() + " , score : " + user.getScore() + " \n"+ "\033[0m");
            
            System.out.print("\033[1;36m"+" Guess my word : "+"\033[0m");
            for (int j = 0; j < partGussed.length; j++) {
                System.out.print(partGussed[j]);
            }System.out.println("");
            
            
            
            if ( (chandbar>6 && selected.length()<=9) || chandbar>13) {break;}
            
            
            System.out.print("\n Your opertunity so far : ");
            for (int j = 0; j < chandbar; j++) {
                System.out.print("\033[0;31m" + "x ");//red print                
            }
            for (int j = chandbar; j < charsInputed.length; j++) {
                System.out.print("\033[1;32m" + "O ");//green print                
            }System.out.println("\033[0m");
            
            
            
            
            if(chandbar!=0) {
                System.out.print(" Your choices so far    : ");
                for (int j = 0; j < charsInputed.length; j++) {
                    System.out.print(charsInputed[j] + " ");
                }System.out.println("");
            }
                
            
            int newchandbar;
            if (selected.length()<=9) {
                newchandbar=chandbar;
            }else{
                newchandbar=chandbar/2;
            }
                         
            //hangman print
            System.out.println(" ----");
                           
            if(newchandbar>0) System.out.println(" |  |");
            else           System.out.println(" |");
            
            if(newchandbar>1) System.out.println(" |  o");
            else           System.out.println(" |");
            
            if(newchandbar>2) System.out.print(" |   \\");
            else           System.out.println(" |");
            if(newchandbar==3)System.out.println("");
            
            if(newchandbar>3) System.out.print("\r |  |\\");
            if(newchandbar==4)System.out.println("");
            
            if(newchandbar>4) System.out.println("\r | /|\\");
            
            if(newchandbar>5) System.out.print(" |   \\");
            else           System.out.println(" |");
            if(newchandbar==6)System.out.println("");
            
            if(newchandbar>6) System.out.println("\r | / \\");
            
            System.out.println(" |");
            
            
            
            if (help) {
                System.out.println("\033[1;36m"+" for just 1 help ,enter <<-1>> in entry (1 point mineses)"+"\033[0m");
            }
            
            
            System.out.print("\033[1;36m"+" Enter your think char  : "+"\033[0m");
            String inputed = in.nextLine();
            
            if (inputed.equals("-1")) {
                
                if (help) {
                    user.score--;

                    int rand;
                    while (true) {                        
                        rand = (int)(Math.random()*selected.length());
                        if(partGussed[rand]=='-')break;
                    }
                    
                    
                    for (int j = 0; j < selected.length(); j++) {
                        if (selected.charAt(j)==selected.charAt(rand)) {
                            partGussed[j]=selected.charAt(rand);
                        }
                    }

                    //win checking
                    int j;
                    for (j = 0; j < partGussed.length; j++) {
                        if (partGussed[j]=='-') {break;}
                    }
                    if (j==partGussed.length) {winner=true;break;}
                    
                    help=false;
                }
                    
                
            }else if (inputed.length()>1) {
               
                System.out.println("Invalid input");
                
            }else{
                
                charsInputed[chandbar] = inputed.charAt(0);



                int k;
                for (k = 0; k<chandbar; k++) {//check that is already exist?
                    if(charsInputed[chandbar]==charsInputed[k]){break;}
                }
                if (chandbar!=k) {//break happend and E k : charsInputed[chandbar]=charsInputed[k]

                    charsInputed[chandbar]=' ';
                    System.err.println("\033[1;31m"+"You entered this already"+ "\033[0m");
                    TimeUnit.SECONDS.sleep(1);

                }else{

                    //input finding char                
                    if (selected.indexOf(charsInputed[chandbar])!=-1) {
                        inputis = true;

                        for (int j = 0; j < selected.length(); j++) {
                            if (selected.charAt(j)==charsInputed[chandbar]) {
                                partGussed[j]=charsInputed[chandbar];
                            }
                        }
                        charsInputed[chandbar]=' ';
                    }

                    //winner checking
                    int j;
                    for (j = 0; j < partGussed.length; j++) {
                        if (partGussed[j]=='-') {break;}
                    }
                    if (j==partGussed.length) {winner=true;break;}



                    if(!inputis) chandbar++;
                                
                }
            
            }
            
            cls();
        }
        
        cls();
        
        
            
            
        System.out.println("\033[41m"+"\n Player : " + user.getUsername() + " , score : " + user.getScore() + " \n"+ "\033[0m");

        if (winner) {
            
            
            System.out.println("\033[0;32m");
            System.out.println(" ----");
            System.out.println(" |  |"); 
            System.out.println(" |");
            System.out.println(" | \\o/");
            System.out.println(" |  |");
            System.out.println(" | / \\");
            
            System.out.println("\n You Won." + "\033[0m");
            System.out.println(" My word that you guss it was : " + selected);
            
            int newScore = user.getScore()+5;
            user.setScore(newScore);
            System.out.println(" add your score 5 point.");
            
            System.out.print("\n back...");
            char chr = in.next().charAt(0);
            
        }else{
            
            System.out.println("\033[1;31m");
            System.out.println(" ----");
            System.out.println(" |  |");               
            System.out.println(" |  o");
            System.out.println(" | /|\\");
            System.out.println(" | / \\");
            System.out.println(" |" );
            
                           
            System.out.println("\n You lost."+ "\033[0m");
            
            System.out.print(" Partin gust : ");
            for (int j = 0; j < partGussed.length; j++) {
                System.out.print(partGussed[j]);
            }System.out.println("");
            
            System.out.println(" My word was : " + selected);
            
            System.out.print("\n back...");
            char chr = in.next().charAt(0);
        }
    }
    
    public static User[] sortUsers(User[]users , int usercount){
        
        //insertion sort
        for (int i = 1; i<usercount; i++) {
            
            int localmin = users[i].score;//key = localmin
            User tempUser = users[i];
            
            int j;
            for (j = i-1; j >= 0; j--) {
                
                if (users[j].score > localmin) {
                    users[j+1]=users[j];
                }else{
                    break;
                }
                
            }
            
            users[j+1]=tempUser;
        }
        
        
        return users;
    }
    
    public static boolean checkPassword(String pass){
        boolean isPass = true;
        
        Pattern pattern1 = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher1 = pattern1.matcher(pass);
        
        Pattern pattern2 = Pattern.compile("[0-9]");
        Matcher matcher2 = pattern2.matcher(pass);
        
        Pattern pattern3 = Pattern.compile("[a-z]");
        Matcher matcher3 = pattern3.matcher(pass);
        
        Pattern pattern4 = Pattern.compile("[A-Z]");
        Matcher matcher4 = pattern4.matcher(pass);
        
        int specific=0;
        
        while (matcher1.find()) {
            specific++;
        }
        
        System.out.print("\033[0;31m");
        
        if (specific==0) {
            System.out.println("\n--add just one specific char");
            isPass=false;
        }
        
        if (specific>1) {
            System.out.println("--there is more than one specific char");
            isPass=false;
        }
        
        if (pass.length()<6) {
            System.out.println("--pass length should be more than 5");
            isPass=false;
        }
        
        if (!matcher2.find()) {
            System.out.println("--add a digit in password");
            isPass=false;
        }
        
        if (!matcher3.find()) {
            System.out.println("--add small letter in password");
            isPass=false;
        }
        
        if (!matcher4.find()) {
            System.out.println("--add capital letter in password");
            isPass=false;
        }
        System.out.println("\033[0m");
        
        return isPass;
    }
    
    public static void cls(){
        try {
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
    }
    
    public static void exit(User[]user,int usercount) throws InterruptedException{
        System.out.println("\033[1;30m"+" Good night"+"\033[0m");
                TimeUnit.SECONDS.sleep(1);
                saveData(user, usercount);
                cls();
                saveData(user, usercount);
                System.exit(0);
    }
}

