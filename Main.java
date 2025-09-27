import java.util.Scanner;

class Main{
private final static int EXIT_CHOICE = 0;
    public static void main(String args[]){
        int moduleChoice;
        do{
        System.out.println("1.add users");
        Scanner scanner = new Scanner(System.in);
        moduleChoice = scanner.nextInt();
        switch(moduleChoice){
            case 1:
                System.out.println("1. List the users");
                System.out.println("2. Add users");
                System.out.println("3. Get user by ID");
                int userChoice = scanner.nextInt();
                switch(userChoice){
                    case 1:
                        UserManager.listUsers(); 
                        break;
                    case 2:
                        System.out.println("enter user name:");
                        String userName = scanner.next();
                        System.out.println("enter user email:");
                        String emailID = scanner.next();
                        System.out.println("enter user password:");
                        String passWord = scanner.next();
                        UserManager.addUser(userName, emailID, passWord);
                        break;
                    case 3:
                        System.out.println("enter the user ID:");
                        int userID = scanner.nextInt();
                        UserManager.getUser(userID);
                        break;
                }
                
        }
    }while(moduleChoice != EXIT_CHOICE);
    }
}