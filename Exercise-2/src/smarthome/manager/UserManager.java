package manager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.User;

public class UserManager {
    // Stores all User objects
    static final List<User> users = new ArrayList<>();

    // Tracks the next unique user ID to assign.
    private static int nextUserID = 1;


    public static User findUserById(int id) {
        for (User user : users) {
            if (user.getUserID() == id) {
                return user;
            }
        }
        return null;
    }


    public static User login(String emailID, String passWord) {
        // Input validation before checking the list
        if (emailID == null || emailID.trim().isEmpty() || passWord == null || passWord.isEmpty()) {
            System.err.println("Login failed: Email and password cannot be empty.");
            return null;
        }

        for (User user : users) {
            // Checks for both email and password match
            if (user.getMailID().equals(emailID) && user.getPassWord().equals(passWord)) {
                System.out.println("Login successful! Welcome, " + user.getUserName() + ".");
                return user;
            }
        }
        System.err.println("Login failed: Invalid email or password.");
        return null;
    }

  
    public static void listUsers() {
        if (users.isEmpty()) {
            System.out.println("No users added yet.");
        } else {
            System.out.println("--- Registered Users ---");
            for (User user : users) {
                System.out.println("ID: " + user.getUserID());
                System.out.println("Name: " + user.getUserName());
                System.out.println("Email: " + user.getMailID());
            }
        }
    }


    public static void addUser(String userName, String emailID, String passWord) {
        // 1. Check if name is empty
        if (userName == null || userName.trim().isEmpty()) {
            System.err.println("User creation failed: User name cannot be empty.");
            return;
        }

        // 2. Validate email format
        if (!isEmailValid(emailID)) {
            System.err.println("User creation failed: The mail ID you have provided is not valid.");
            return;
        }

        // 3. Check for duplicate email
        if (findUserByEmail(emailID) != null) {
            System.err.println("User creation failed: The mail ID already exists.");
            return;
        }
        
        // 4. Validate password strength/format
        if (!isPassWordValid(passWord)) {
            System.err.println("User creation failed: The password must be at least 8 characters and include uppercase, lowercase, a digit, and a symbol.");
            return;
        }
        
        users.add(new User(nextUserID, userName.trim(), emailID.trim(), passWord));
        System.out.println("User added successfully with ID: " + nextUserID);
        nextUserID++;
    }

 
    public static void getUser(int userID) {
        User user = findUserById(userID);
        if (user != null) {
            System.out.println("User found:");
            System.out.println("ID: " + user.getUserID());
            System.out.println("Name: " + user.getUserName());
            System.out.println("Email: " + user.getMailID());
        } else {
            System.err.println("The user with ID " + userID + " does not exist.");
        }
    }
    

    private static User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getMailID().equalsIgnoreCase(email.trim())) {
                return user;
            }
        }
        return null;
    }


    public static boolean isEmailValid(String email) {
        // Simple regex: one or more non-space characters, then @, then one or more non-space, then dot, then one or more non-space.
        String regex = "^\\S+@\\S+\\.\\S+$";
        Pattern pattern = Pattern.compile(regex);

        if (email == null) return false;
        Matcher matcher = pattern.matcher(email.trim());
        return matcher.matches();
    }


    public static boolean isPassWordValid(String passWord) {
        // Pattern: Minimum 8 characters, at least one lowercase, one uppercase, one digit, and one symbol.
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(regex);

        if (passWord == null) return false;
        Matcher matcher = pattern.matcher(passWord);
        return matcher.matches();
    }
}