import java.sql.*;
import java.util.ArrayList;

public class UserDAO {
    private ArrayList<User> UserDAO = new ArrayList<>();

    public UserDAO(){
        retreiveUsers();
    }

    public void retreiveUsers(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = ConnectionManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM User");

            while (resultSet.next()){
                int userID = resultSet.getInt("UserID"); 
                String userName = resultSet.getString("Username");
                String pwd = resultSet.getString("Password");
                String type = resultSet.getString("Usertype");

                this.UserDAO.add(new User(userID, userName, pwd, type));
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertUser(User user){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sql = "INSERT INTO User VALUES (?, ?, ?, ?)";
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, "" + user.getUserID());
            preparedStatement.setString(2, "" + user.getUserName());
            preparedStatement.setString(3, "" + user.getPassword());
            preparedStatement.setString(4, "" + user.getUserType());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            } else {
                System.out.println("Failed to insert user.");
            }

            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<User> getUserDAO(){
        return this.UserDAO;
    }
}
