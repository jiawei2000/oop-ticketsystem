package oop.ticketing_system.models;

import java.util.ArrayList;
import java.util.List;

public class TransactionTickets {
    private Transaction transaction;
    private List<Ticket> tickets;
    private double amountSpent;
    private String eventName;

    public TransactionTickets(Transaction transaction) {
        this.transaction = transaction;
        this.tickets = new ArrayList<>();
    }

    public Transaction getTransaction() {
        return this.transaction;
    }

    public List<Ticket> getTickets() {
        return this.tickets;
    }

    public double getAmountSpent() {
        return this.amountSpent;
    }

    public String getEventName() {
        return this.eventName;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void setAmountSpent(Double amountSpent) {
        this.amountSpent = amountSpent;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }
}
