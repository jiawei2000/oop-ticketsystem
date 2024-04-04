package oop.ticketing_system.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "TicketOfficer")
public class TicketOfficer extends User {
    private int eventId;

    public TicketOfficer() {
        super();
        super.setType("TicketOfficer");
    }


    public TicketOfficer(int userId, String userName, String password, int eventId) {
        super(userId, userName, password, "TicketOfficer");
        this.eventId = eventId;
    }

    public int getEventId() {
        return this.eventId;
    }

    public String toString() {
        return super.toString() + ", EventId: " + eventId;
    }

}
