package oop.ticketing_system.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;
    private int eventId;
    private int userId;
    private int numTicketPurchased;
    private String status;

    public Transaction() {
    }

    public Transaction(int transactionId, int eventId, int userId, int numTicketPurchased, String status) {
        this.transactionId = transactionId;
        this.eventId = eventId;
        this.userId = userId;
        this.numTicketPurchased = numTicketPurchased;
        this.status = status;
    }

    // getters
    public int getTransactionId() {
        return this.transactionId;
    }

    public int getEventId() {
        return this.eventId;
    }

    public int getUserId() {
        return this.userId;
    }

    public int getNumTicketPurchased() {
        return this.numTicketPurchased;
    }

    public String getStatus() {
        return this.status;
    }

    public String toString() {
        return String.format("TransactionId: %d, EventId: %d, UserId: %d, NumTicketPurchased: %d, Status: %s", transactionId, eventId, userId, numTicketPurchased, status);
    }

    // setters
    public void setStatus(String status) {
        this.status = status;
    }
}
