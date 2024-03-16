public class Customer extends User {
    private String email; 
    private double balance; 

    public Customer(int userID, String userName, String pwd, String type, String email, double balance){
        super(userID, userName, pwd, type);
        this.email = email;
        this.balance = balance;
    }

    //getter 
    public String getEmail(){
        return this.email;
    }
    public double getBalance(){
        return this.balance;
    }

    public String toString(){
        return super.toString() + String.format(", Email: %s, Balance: %.2f", email, balance); 
    }
}
