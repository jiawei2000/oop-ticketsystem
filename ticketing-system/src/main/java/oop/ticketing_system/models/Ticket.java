package oop.ticketing_system.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int TicketId;
    private int EventId;
    private int UserId;
    private String type;
    private String status;

    public Ticket() {
    }

    public Ticket(int ticketId, int eventId, int userId, String type, String status) {
        this.TicketId = ticketId;
        this.EventId = eventId;
        this.UserId = userId;
        this.type = type;
        this.status = status;
    }

    public String toString() {
        return String.format("TicketId: %d, EventId: %d, UserId: %d, Type: %s, Status: %s", TicketId, EventId, UserId, type, status);
    }

    //getter methods 
    public int getTicketId() {
        return this.TicketId;
    }

    public int getEventId() {
        return this.EventId;
    }

    public int getUserId() {
        return this.UserId;
    }

    public String getType() {
        return this.type;
    }

    public String getStatus() {
        return this.status;
    }

}
