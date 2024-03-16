package oop.ticketing_system.models;

public class Transaction {
    private int transactionID;
    private int eventID;
    private int userID;
    private int numTicketPurchased;
    private String status;

    public Transaction(int transactionID, int eventID, int userID, int numTicketPurchased, String status) {
        this.transactionID = transactionID;
        this.eventID = eventID;
        this.userID = userID;
        this.numTicketPurchased = numTicketPurchased;
        this.status = status;
    }

    // getters
    public int getTransactionID() {
        return this.transactionID;
    }

    public int getEventID() {
        return this.eventID;
    }

    public int getUserID() {
        return this.userID;
    }

    public int getNumTicketPurchased() {
        return this.numTicketPurchased;
    }

    public String getStatus() {
        return this.status;
    }

    public String toString() {
        return String.format("TransactionID: %d, EventID: %d, UserID: %d, NumTicketPurchased: %d, Status: %s",
                transactionID, eventID, userID, numTicketPurchased, status);
    }
}
