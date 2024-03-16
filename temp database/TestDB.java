import java.util.*;

public class TestDB {
    public static void main(String[] args) {
        CustomerDAO customers = new CustomerDAO();
        UserDAO users = new UserDAO();
        TicketOfficerDAO ticketofficers = new TicketOfficerDAO();

        System.out.println(ticketofficers.getTicketOfficerDAO());

    }
}