import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User{
    private int userID;
    private String userName;
    private String emailID;
    private String passWord;
    public User(int userID,String userName, String emailID, String passWord) {
        this.userID = userID;
        this.userName = userName;
        this.emailID = emailID;
        this.passWord = passWord;
    }
    public void setUserID(int userID){
        this.userID = userID;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setMailID(String emailID){
        this.emailID = emailID;
    }
    public void setPassWord(String passWord){
        this.passWord = passWord;
    }
    public String getUserName(){
        return userName;
    }
    public String getMailID(){
        return emailID;
    }
    public String getPassWord(){
        return passWord;
    }
    public int getUserID(){
        return userID;
    }
    
}
