import java.util.*;

public class TestDB {
    public static void main(String[] args) {
        CustomerDAO customers = new CustomerDAO();
        UserDAO users = new UserDAO();
        TicketOfficerDAO ticketofficers = new TicketOfficerDAO();

        ticketofficers.insertTicketOfficer(new TicketOfficer(5, "userTest5", "pass123", "T", 1));
    }
}