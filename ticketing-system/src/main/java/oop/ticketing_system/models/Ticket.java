package oop.ticketing_system.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;
    private int eventId;
    private int transactionId;
    private int userId;
    private String type;
    private String status;

    public Ticket() {
    }

    public Ticket(int ticketId, int eventId, int userId, int transactionId, String type, String status) {
        this.ticketId = ticketId;
        this.eventId = eventId;
        this.transactionId = transactionId;
        this.userId = userId;
        this.type = type;
        this.status = status;
    }

    public String toString() {
        return "Ticket ID: " + this.ticketId + ", Event ID: " + this.eventId + ", User ID: " + this.userId + ", Transaction ID: " + this.transactionId + ", Type: " + this.type + ", Status: " + this.status;
    }

    //getter methods 
    public int getTicketId() {
        return this.ticketId;
    }

    public int getEventId() {
        return this.eventId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getType() {
        return this.type;
    }

    public String getStatus() {
        return this.status;
    }

    // setters
    public void setStatus(String status) {
        this.status = status;
    }

}
