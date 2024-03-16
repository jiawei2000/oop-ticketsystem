package oop.ticketing_system.models;

public class Ticket {
    private int TicketID;
    private int EventID;
    private int UserID;
    private String type;
    private String status;

    public Ticket(int ticketID, int eventID, int userID, String type, String status) {
        this.TicketID = ticketID;
        this.EventID = eventID;
        this.UserID = userID;
        this.type = type;
        this.status = status;
    }

    public String toString() {
        return String.format("TicketID: %d, EventID: %d, UserID: %d, Type: %s, Status: %s", TicketID, EventID, UserID,
                type, status);
    }

    //getter methods 
    public int getTicketID(){
        return this.TicketID;
    }

    public int getEventID(){
        return this.EventID;
    }

    public int getUserID(){
        return this.UserID;
    }

    public String getType(){
        return this.type;
    }

    public String getStatus(){
        return this.status;
    }

}
