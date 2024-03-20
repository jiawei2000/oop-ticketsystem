package oop.ticketing_system.services;

import oop.ticketing_system.models.*;
import oop.ticketing_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

//java time package
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    public List<Event> displayEvents() {
        return eventRepository.findAll();
    }

    public List<Transaction> displayTransactions(int customerId) {
        return transactionRepository.findByUserId(customerId);
    }

    public List<Ticket> displayPurchasedTickets(int customerId) {
        return ticketRepository.findByUserId(customerId);
    }

    public Transaction processTransaction(Transaction transaction) {
        // EventIDretrieve for event details
        // Customer ID //retrieve balance
        //should also check for existing purchased tickets
        int qtyPurchased = transaction.getNumTicketPurchased();
        int eventId = transaction.getEventId();
        int customerId = transaction.getUserId();

        Event event = eventRepository.getReferenceById(eventId);
        Customer customer = customerRepository.getReferenceById(customerId);

        // check1 for booking must be witin 6months in advance and 24hrs before booking
        // time
        String eventDate = event.getDate();
        String eventTime = event.getTime();
        boolean temp = isBookingValid(eventDate, eventTime, LocalDateTime.now());
        if (!temp) {
            throw new IllegalArgumentException("purchase not within timeframe");
        }

        // check2 for qty limit
        if (qtyPurchased > 5) {
            throw new IllegalArgumentException("Purchased quantity is over maximum limit");
        }

        // check3 if qty > stock
        if (qtyPurchased > event.getStock()) {
            throw new IllegalArgumentException("Insufficient stock");
        }

        // check4 for sufficent balance
        double totalCost = qtyPurchased * event.getPrice();
        if (totalCost > customer.getBalance()) {
            throw new IllegalArgumentException("Insufficient Account Balance");
        }

        // once all conditions are met, ticket creation
        for (int i = 0; i < qtyPurchased; i++) {
//            ticketRepository.save(new Ticket(0, eventId, customerId, "Online", "Active"));
        }

        // update bank balance
        customer.setBalance(customer.getBalance() - totalCost);
        customerRepository.save(customer);

        // update ticket stock
        event.setStock(event.getStock() - qtyPurchased);
        eventRepository.save(event);

        // create and return transaction
        return transactionRepository.save(transaction);
    }

    public void processTicketCancellation(int ticketId) {
        // conditions to refund ticket: only can cancel 48hours before event
        //output: delete ticket from ticket table, update bank balance, update transaction status, increment stock 
        
        Ticket ticket = ticketRepository.getReferenceById(ticketId);
        Event event = eventRepository.getReferenceById(ticket.getEventId());
        Customer customer = customerRepository.getReferenceById(ticket.getUserId());

        boolean temp = isCancellationValid(event.getDate(), event.getTime(), LocalDateTime.now());
        if (!temp){
            throw new IllegalArgumentException("Cancellation Error: cancellation must be made 48 hours before Event start time"); 
        }

        //update bank balance 
        double cusBalance = customer.getBalance();
        double ticketPrice = event.getPrice();
        double cancelPrice = event.getCancellationFee();
        customer.setBalance(cusBalance + ticketPrice - cancelPrice);
        customerRepository.save(customer);

        //increment stock
        int currStock = event.getStock();
        event.setStock(currStock+1);
        eventRepository.save(event);

        //update transaction {have logical issue atm}, should also update event revenue. 

        //delete ticket
        ticketRepository.delete(ticket);
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
