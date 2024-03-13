import java.sql.*;

public class ConnectionManager {

    public Connection getConnection() {
        String endpoint = "oop-project.c7sgqu6yoiyv.ap-southeast-1.rds.amazonaws.com";
        String port = "3306";
        String db_name = "OOP_Project";
        String uName = "admin";
        String password = "Password123";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    String.format("jdbc:mysql://%s:%s/%s", endpoint, port, db_name),
                    uName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
