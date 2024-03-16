import java.sql.*;
import java.util.ArrayList;

public class TicketOfficerDAO {
    private ArrayList<TicketOfficer> ticketOfficerDAO = new ArrayList<>();

    public TicketOfficerDAO() {
        retrieveTicketOfficers();
    }

    public void retrieveTicketOfficers() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = ConnectionManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT u.UserID, u.Username, u.Password, u.Usertype, t.EventID FROM User u LEFT JOIN TicketOfficer t ON t.UserID = u.UserID WHERE u.UserType = 'T'");
            while (resultSet.next()){
                int userID = resultSet.getInt("UserID"); 
                String userName = resultSet.getString("Username");
                String pwd = resultSet.getString("Password");
                String type = resultSet.getString("Usertype");
                int eventID = resultSet.getInt("EventID");
                this.ticketOfficerDAO.add(new TicketOfficer(userID, userName, pwd, type, eventID));
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void insertTicketOfficer(TicketOfficer ticketOfficer){
        new UserDAO().insertUser(ticketOfficer);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sql = "INSERT INTO TicketOfficer VALUES (?, ?)";
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, "" + ticketOfficer.getUserID());
            preparedStatement.setString(2, "" + ticketOfficer.getEventID());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new TicketOfficer was inserted successfully!");
            } else {
                System.out.println("Failed to insert TicketOfficer.");
            }

            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<TicketOfficer> getTicketOfficerDAO(){
        return this.ticketOfficerDAO;
    }
}
