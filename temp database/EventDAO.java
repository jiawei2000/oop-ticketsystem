import java.sql.*;
import java.util.ArrayList;

public class EventDAO {
    private ArrayList<Event> eventDAO = new ArrayList<>();

    public EventDAO() {
        retrieveEvents();
    }

    public void retrieveEvents() {
        String endpoint = "oop-project.c7sgqu6yoiyv.ap-southeast-1.rds.amazonaws.com";
        String port = "3306";
        String db_name = "OOP_Project";
        String uName = "admin";
        String password = "Password123";
        try {
            // create connection
            // Connection connection = DriverManager
            // .getConnection(String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s",
            // endpoint, port, db_name,
            // uName, password));

            Class.forName("com.mysql.cj.jdbc.Driver");


            Connection connection = DriverManager.getConnection(
                    String.format("jdbc:mysql://%s:%s/%s", endpoint, port, db_name),
                    uName, password);
            // Create a statement
            Statement statement = connection.createStatement();

            // Execute a query
            ResultSet resultSet = statement.executeQuery("SELECT * FROM EVENT");

            while (resultSet.next()) {
                int eventID = resultSet.getInt("EventID");
                int userID = resultSet.getInt("UserID");
                String eventName = resultSet.getString("Event_Name");
                String venue = resultSet.getString("Venue");
                String date = resultSet.getString("Event_Date");
                String time = resultSet.getString("Event_Time");
                double price = resultSet.getDouble("Price");
                int stock = resultSet.getInt("Stock");
                this.eventDAO.add(new Event(eventID, userID, eventName, venue, date, time, price, stock));
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public ArrayList<Event> getEventDAO() {
        return this.eventDAO;
    }

}
