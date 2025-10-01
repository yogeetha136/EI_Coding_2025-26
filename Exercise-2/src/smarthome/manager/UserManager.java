package manager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.User;

public class UserManager{
        static List<User> users= new ArrayList<>();
        private static int userID = 1;

        public static User findUserById(int id) {
        for (User user : users) {
            if (user.getUserID() == id) {
                return user;
            }
        }
        return null;
    }
    
    public static User login(String emailID, String passWord) {
        for (User user : users) {
            if (user.getMailID().equals(emailID) && user.getPassWord().equals(passWord)) {
                System.out.println("Login successful! Welcome, " + user.getUserName() + ".");
                return user; 
            }
        }
        System.out.println("Login failed: Invalid email or password.");
        return null; 
    }
    public static void listUsers(){
        if(users.isEmpty()){
            System.out.println("no users aaded yet");
        }
        else{
        for(int i=0; i<users.size(); i++){
            System.out.println(users.get(i).getUserID());
            System.out.println(users.get(i).getUserName());
            System.out.println(users.get(i).getMailID());
        }
    }
    }
    public static void addUser(String userName, String emailID, String passWord){
        if(!isEmailValid(emailID)){
            System.out.println("the mail ID you have provided is not valid.");
            return;
        }
        if(users.contains(emailID)){
            System.out.println("the mail ID you have provided is already exists.");
            return;
        }
        if(!isPassWordValid(passWord)){
            System.out.println("the password you have provided is not valid.");
            return;
        }
        if(users.contains(passWord)){
            System.out.println("the password you have provided is already exists.");
            return;
        }
        users.add(new User(userID,userName,emailID,passWord));
        System.out.println("user added successfully");
        userID++;
    }

public static void getUser(int userID){
    boolean found = false;
    for(int i = 0; i < users.size(); i++){
        if(users.get(i).getUserID() == userID){
            System.out.println("User found:");
            System.out.println("ID: " + users.get(i).getUserID());
            System.out.println("Name: " + users.get(i).getUserName());
            System.out.println("Email: " + users.get(i).getMailID());
            found = true;
            break; 
        }
    }
    if(!found){
        System.out.println("The user with this user ID not exists.");
    }
}

    public static boolean isEmailValid(String email) {
        // Pattern: Starts with non-space, then @, then non-space, then dot (.), then non-space
        String regex = "^\\S+@\\S+\\.\\S+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isPassWordValid(String passWord){
        // Pattern: Contains a password of EXACTLY 10 characters, requiring at least one lowercase, one uppercase, one digit, and one symbol
    String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(passWord);
        return matcher.matches();
    }
}