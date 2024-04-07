package oop.ticketing_system.models;

import java.util.ArrayList;
import java.util.List;

public class TransactionTickets {
    private Transaction transaction;
    private List<Ticket> tickets;

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

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }
}
