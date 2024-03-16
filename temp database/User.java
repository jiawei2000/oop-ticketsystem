public class User {
    private int userID; 
    private String userName; 
    private String pwd; 
    private String type;

    public User(int userID, String userName, String pwd, String type){
        this.userID = userID;
        this.userName = userName;
        this.pwd = pwd; 
        this.type = type;
    }

    //getter 
    public int getUserID(){
        return this.userID;
    }
    public String getUserName(){
        return this.userName;
    }
    public String getPassword(){
        return this.pwd;
    }
    public String getUserType(){
        return this.type;
    }

    public String toString(){
        return String.format("UserID: %d, Username: %s, Password: %s, Type: %s", userID, userName, pwd, type);
    }

}
