package oop.ticketing_system.services;

import oop.ticketing_system.models.Customer;
import oop.ticketing_system.models.Event;
import oop.ticketing_system.models.Ticket;
import oop.ticketing_system.models.Transaction;
import oop.ticketing_system.repository.CustomerRepository;
import oop.ticketing_system.repository.EventRepository;
import oop.ticketing_system.repository.TicketRepository;
import oop.ticketing_system.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Transaction purchaseTicket(Transaction transaction, String type) {

        if (!checkSufficientStock(transaction.getEventId(), transaction.getNumTicketPurchased())) {
            throw new IllegalArgumentException("Insufficient stock for event " + transaction.getEventId());
        }

        //Create Transaction
        Transaction newTransaction = transactionRepository.save(transaction);
        int qty = newTransaction.getNumTicketPurchased();

        //Create Tickets
        for (int i = 0; i < qty; i++) {
            Ticket ticketTemplate = new Ticket(0, newTransaction.getEventId(), newTransaction.getUserId(), newTransaction.getTransactionId(), type, "Active");
            ticketRepository.save(ticketTemplate);
        }

        //Update Ticket qty in Event
        Event event = eventRepository.getReferenceById(transaction.getEventId());
        event.setStock(event.getStock() - qty);
        eventRepository.save(event);

        return newTransaction;
    }

    public boolean checkSufficientStock(int eventId, int requestedQty) {
        Event event = eventRepository.getReferenceById(eventId);
        //Check if enough qty
        int remainingQty = event.getStock();
        return remainingQty >= requestedQty;
    }

    public List<Transaction> updateTransactionStatusByEventId(int eventId, String newStatus) {
        List<Transaction> transactions = transactionRepository.findByEventIdAndStatus(eventId, "Active");
        for (Transaction transaction : transactions) {
            transaction.setStatus(newStatus);
        }
        return transactionRepository.saveAll(transactions);
    }

    public List<Transaction> refundUsersByEventId(int eventId, double amount) {
        List<Transaction> transactions = transactionRepository.findByEventIdAndStatus(eventId, "Cancelled");
        for (Transaction transaction : transactions) {
            addCustomerBalance(transaction.getUserId(), amount * transaction.getNumTicketPurchased());
        }
        return transactions;
    }

    public Customer addCustomerBalance(int customerId, double amountToAdd) {
        Customer customer = customerRepository.getReferenceById(customerId);
        double newBalance = customer.getBalance() + amountToAdd;
        customer.setBalance(newBalance);
        return customerRepository.save(customer);
    }
}
