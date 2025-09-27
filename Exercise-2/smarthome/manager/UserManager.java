package smarthome.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import smarthome.model.User;

public class UserManager{
        static List<User> users= new ArrayList<>();
        private static int userID = 1;
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
        //To Do: check using stream instead of looping
        for(int i=0; i<users.size(); i++){
            if(users.get(i).getUserID() == userID){
                System.out.println(users.get(i).getUserID());
                System.out.println(users.get(i).getUserName());
                System.out.println(users.get(i).getMailID());                
            }
            else{
                System.out.println("The user with this user ID not exists.");
                break;
            }
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
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(passWord);
        return matcher.matches();
    }
}