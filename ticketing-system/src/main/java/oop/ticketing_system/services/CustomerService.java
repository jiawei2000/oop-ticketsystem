package oop.ticketing_system.services;

import oop.ticketing_system.models.*;
import oop.ticketing_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.*;

//java time package
import java.time.*;

@Service
public class CustomerService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionService transactionService;

    public List<Transaction> displayTransactions(int customerId) {
        return transactionRepository.findByUserId(customerId);
    }

    public List<Ticket> displayPurchasedTickets(int customerId) {
        return ticketRepository.findByUserId(customerId);
    }

    public TransactionTickets processTransaction(Transaction transaction) {
        // EventIDretrieve for event details
        // Customer ID //retrieve balance
        // should also check for existing purchased tickets
        int qtyPurchased = transaction.getNumTicketPurchased();
        int eventId = transaction.getEventId();
        int customerId = transaction.getUserId();

        Event event = eventRepository.getReferenceById(eventId);

        Customer customer = customerRepository.getReferenceById(customerId);

        // check1 for booking must be witin 6months in advance and 24hrs before booking time
        String eventDate = event.getDate();
        String eventTime = event.getTime();
        boolean temp = isBookingValid(eventDate, eventTime, LocalDateTime.now());
        if (!temp) {
            throw new IllegalArgumentException("Purchase must be made within 6 months in advance and at least 24 hours before the booking time.");
        }

        // check2 for qty limit
        if (qtyPurchased > 5 || qtyPurchased<=0) {
            throw new IllegalArgumentException("The purchase quantity exceeds 5 or is equal to or less than 0.");
        }

        List<Transaction> previousTransactions = transactionRepository.findByEventIdAndUserId(eventId, customerId);
        int ticketCount = 0;
        for (Transaction tran : previousTransactions) {
            if (tran.getStatus().equals("Active")) {
                ticketCount += tran.getNumTicketPurchased();
            }
        }
        // check3 check for existing active transaction
        if (qtyPurchased > 5 - ticketCount) {
            throw new IllegalArgumentException(
                    "Purchase limit is 5. you currently have " + ticketCount + " active tickets");
        }

        // check4 for sufficent balance
        double totalCost = qtyPurchased * event.getPrice();
        if (totalCost > customer.getBalance()) {
            throw new IllegalArgumentException("Insufficient Account Balance. Total Cost of the transaction is $" + totalCost);
        }

        // once all conditions are met, ticket creation
        // Create Transaction & Tickets
        TransactionTickets transactionTickets = transactionService.purchaseTicket(transaction, "Online");

        // update bank balance
        customer.setBalance(customer.getBalance() - totalCost);
        customerRepository.save(customer);

        // Return Transaction
        return transactionTickets;
    }

    public Transaction processTransactionCancellation(int transactionId) {
        // conditions to refund ticket: only can cancel 48hours before event
        // new output: change ticket status, change transaction status, update
        // bankbalance, increment stock

        Transaction transaction = transactionRepository.getReferenceById(transactionId);
        Event event = eventRepository.getReferenceById(transaction.getEventId());
        Customer customer = customerRepository.getReferenceById(transaction.getUserId());

        boolean temp = isCancellationValid(event.getDate(), event.getTime(), LocalDateTime.now());
        if (!temp) {
            throw new IllegalArgumentException(
                    "Cancellation Error: cancellation must be made 48 hours before Event start time");
        }

        // update bank balance
        double cusBalance = customer.getBalance();
        double totalRefundAmt = (event.getPrice() - event.getCancellationFee()) * transaction.getNumTicketPurchased();
        customer.setBalance(cusBalance + totalRefundAmt);
        customerRepository.save(customer);

        // increment stock
        int currStock = event.getStock();
        event.setStock(currStock + transaction.getNumTicketPurchased());
        eventRepository.save(event);

        // change ticket status to refunded
        List<Ticket> tickets = ticketRepository.findByTransactionId(transactionId);
        for (Ticket ticket : tickets) {
            ticket.setStatus("Refunded");
            ticketRepository.save(ticket);
        }
        transaction.setStatus("Refunded");
        return transactionRepository.save(transaction);

    }

    // helper function
    public boolean isBookingValid(String eventDate, String eventTime, LocalDateTime bookingTime) {

        String[] tempDate = eventDate.split("-");
        String[] tempTime = eventTime.split(":");
        LocalDateTime eventStartTime = LocalDateTime.of(Integer.parseInt(tempDate[0]), Integer.parseInt(tempDate[1]),
                Integer.parseInt(tempDate[2]), Integer.parseInt(tempTime[0]), Integer.parseInt(tempTime[1]));

        // Calculate the maximum booking time (6 months in advance)
        LocalDateTime maxBookingTime = eventStartTime.minusMonths(6);

        // Calculate the minimum booking time (24 hours before the event)
        LocalDateTime minBookingTime = eventStartTime.minusHours(24);

        // Check if the booking time is within the valid range
        return !bookingTime.isBefore(maxBookingTime) && !bookingTime.isAfter(minBookingTime);
    }

    public boolean isCancellationValid(String eventDate, String eventTime, LocalDateTime cancelTime) {
        String[] tempDate = eventDate.split("-");
        String[] tempTime = eventTime.split(":");
        LocalDateTime eventStartTime = LocalDateTime.of(Integer.parseInt(tempDate[0]), Integer.parseInt(tempDate[1]),
                Integer.parseInt(tempDate[2]), Integer.parseInt(tempTime[0]), Integer.parseInt(tempTime[1]));

        // Calculate minmium cancel time
        LocalDateTime minCancelTime = eventStartTime.minusHours(48);
        // returns true is cancel time is after the min cancel time
        return !cancelTime.isAfter(minCancelTime);
    }
}
