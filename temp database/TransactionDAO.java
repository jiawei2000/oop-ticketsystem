import java.sql.*;
import java.util.ArrayList;

public class TransactionDAO {
    private ArrayList<Transaction> transactionDAO = new ArrayList<>();

    public TransactionDAO() {
        retrieveTransactions();
    }

    public void retrieveTransactions() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = ConnectionManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Transaction");

            while (resultSet.next()) {
                int transactionID = resultSet.getInt("TransactionID");
                int eventID = resultSet.getInt("EventID");
                int userID = resultSet.getInt("UserID");
                int numTicketPurchased = resultSet.getInt("NumTicketPurchased");
                String status = resultSet.getString("Status");
                this.transactionDAO.add(new Transaction(transactionID, eventID, userID, numTicketPurchased, status));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertTransaction(Transaction transaction) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sql = "INSERT INTO Transaction VALUES (?, ?, ?, ?, ?)";
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, "" + transaction.getTransactionID());
            preparedStatement.setString(2, "" + transaction.getEventID());
            preparedStatement.setString(3, "" + transaction.getUserID());
            preparedStatement.setString(4, "" + transaction.getNumTicketPurchased());
            preparedStatement.setString(5, "" + transaction.getStatus());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new transaction was inserted successfully!");
            } else {
                System.out.println("Failed to insert transaction.");
            }

            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Transaction> getTransactionDAO() {
        return this.transactionDAO;
    }
}
