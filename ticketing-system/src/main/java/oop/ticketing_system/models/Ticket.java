package oop.ticketing_system.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;
    private int eventId;
    private int userId;
    private String type;
    private String status;

    public Ticket() {
    }

    public Ticket(int ticketId, int eventId, int userId, String type, String status) {
        this.ticketId = ticketId;
        this.eventId = eventId;
        this.userId = userId;
        this.type = type;
        this.status = status;
    }

    public String toString() {
        return String.format("TicketId: %d, EventId: %d, UserId: %d, Type: %s, Status: %s", ticketId, eventId, userId, type, status);
    }

    //getter methods 
    public int getTicketId() {
        return this.ticketId;
    }

    public int getEventId() {
        return this.eventId;
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

}
