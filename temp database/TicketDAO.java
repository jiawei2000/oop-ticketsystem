import java.sql.*;
import java.util.ArrayList;

public class TicketDAO {
    private ArrayList<Ticket> ticketDAO = new ArrayList<>();

    public TicketDAO() {
        retrieveTickets();
    }

    public void retrieveTickets() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = ConnectionManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Ticket");

            while (resultSet.next()) {
                int ticketID = resultSet.getInt("TicketID");
                int eventID = resultSet.getInt("EventID");
                int userID = resultSet.getInt("UserID");
                String type = resultSet.getString("Type");
                String status = resultSet.getString("Status");
                this.ticketDAO.add(new Ticket(ticketID, eventID, userID, type, status));
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Ticket> getTicketDAO() {
        return this.ticketDAO;
    }

    public void insertTicket(Ticket tic) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sql = "INSERT INTO Ticket VALUES (?, ?, ?, ?, ?)"; 
            Connection connection = ConnectionManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ""+tic.getTicketID());
            preparedStatement.setString(2, ""+tic.getEventID());
            preparedStatement.setString(3, ""+tic.getUserID());
            preparedStatement.setString(4, tic.getType());
            preparedStatement.setString(5, tic.getStatus());
            
            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new Ticket was inserted successfully!");
            } else {
                System.out.println("Failed to insert Ticket.");
            }

            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
