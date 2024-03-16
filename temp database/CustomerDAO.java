import java.sql.*;
import java.util.ArrayList;

public class CustomerDAO {
    private ArrayList<Customer> CustomerDAO = new ArrayList<>();

    public CustomerDAO() {
        retrieveCustomers();
    }

    public void retrieveCustomers() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = ConnectionManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT u.UserID, u.Username, u.Password, u.Usertype, c.Email, c.Balance FROM User u LEFT JOIN Customer c ON c.UserID = u.UserID where u.UserType = 'C';");

            while (resultSet.next()) {
                int userID = resultSet.getInt("UserID");
                String userName = resultSet.getString("Username");
                String pwd = resultSet.getString("Password");
                String type = resultSet.getString("Usertype");
                String email = resultSet.getString("Email");
                double balance = resultSet.getDouble("Balance");
                this.CustomerDAO.add(new Customer(userID, userName, pwd, type, email, balance));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertCustomer(Customer customer){
        new UserDAO().insertUser(customer);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sql = "INSERT INTO Customer VALUES (?, ?, ?)";
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, "" + customer.getUserID());
            preparedStatement.setString(2, "" + customer.getEmail());
            preparedStatement.setString(3, "" + customer.getBalance());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new customer was inserted successfully!");
            } else {
                System.out.println("Failed to insert customer.");
            }

            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Customer> getCustomerDAO() {
        return this.CustomerDAO;
    }

}
