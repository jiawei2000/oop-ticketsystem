import java.util.*;
public class TestDB {
    public static void main(String[] args) {
        // EventDAO events = new EventDAO();
        // System.out.println(events.getEventDAO());

        TicketDAO tickets = new TicketDAO();
        // tickets.insertTicket(new Ticket(1, 1, 2,"Online", "Active"));
        System.out.println(tickets.getTicketDAO());
    } 
}