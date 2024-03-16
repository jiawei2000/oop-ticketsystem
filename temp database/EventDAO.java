import java.sql.*;
import java.util.ArrayList;

public class EventDAO {
    private ArrayList<Event> eventDAO = new ArrayList<>();

    public EventDAO() {
        retrieveEvents();
    }

    public void retrieveEvents() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = ConnectionManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Event");

            while (resultSet.next()) {
                int eventID = resultSet.getInt("EventID");
                int userID = resultSet.getInt("UserID");
                String eventName = resultSet.getString("Event_Name");
                String venue = resultSet.getString("Venue");
                String date = resultSet.getString("EventDate");
                String time = resultSet.getString("EventTime");
                double price = resultSet.getDouble("Price");
                int stock = resultSet.getInt("Stock");
                double cFee = resultSet.getDouble("CancellationFee");
                this.eventDAO.add(new Event(eventID, userID, eventName, venue, date, time, price, stock, cFee));
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Event> getEventDAO() {
        return this.eventDAO;
    }

    public void insertEvent(Event event) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sql = "INSERT INTO Event VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, "" + event.getEventID());
            preparedStatement.setString(2, "" + event.getUserID());
            preparedStatement.setString(3, "" + event.getEventName());
            preparedStatement.setString(4, "" + event.getVenue());
            preparedStatement.setString(5, "" + event.getDate());
            preparedStatement.setString(6, "" + event.getTime());
            preparedStatement.setString(7, "" + event.getPrice());
            preparedStatement.setString(8, "" + event.getStock());
            preparedStatement.setString(9, "" + event.getCancellationFee());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new event was inserted successfully!");
            } else {
                System.out.println("Failed to insert event.");
            }

            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
}
