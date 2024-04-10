package oop.ticketing_system.services;

import oop.ticketing_system.models.*;
import oop.ticketing_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private EventService eventService;
    @Autowired
    private EmailService emailService;

    public List<Transaction> displayTransactions(int customerId) {
        return transactionRepository.findByUserId(customerId);
    }

    public List<TransactionTickets> displayTransactionTickets(int customerId) {
        List<TransactionTickets> transactionTicketsList = new ArrayList<>();
        List<Transaction> transactionList = displayTransactions(customerId);

        for (Transaction transaction : transactionList) {
            TransactionTickets transactionTickets = new TransactionTickets(transaction);
            Event event = eventService.getEventById(transaction.getEventId());
            transactionTickets.setEventName(event.getEventName());

            List<Ticket> ticketList = ticketRepository.findByTransactionId(transaction.getTransactionId());
            transactionTickets.setTickets(ticketList);

            transactionTickets.setAmountSpent(event.getPrice() * transaction.getNumTicketPurchased());

            transactionTicketsList.add(transactionTickets);
        }

        return transactionTicketsList;
    }

    public List<Ticket> displayPurchasedTickets(int customerId) {
        return ticketRepository.findByUserId(customerId);
    }

    public List<Event> displayCustomerEvent(int customerId) {
        //retrieve customer transaction with active status 
        List<Transaction> transactions = transactionRepository.findByUserIdAndStatus(customerId, "Active");

        // get all unique eventIds
        List<Integer> eventIds = new ArrayList<>();
        for (Transaction transaction : transactions) {
            int currEventId = transaction.getEventId();
            if (!eventIds.contains(currEventId)) {
                eventIds.add(currEventId);
            }
        }
        // List of Events to be returned
        List<Event> events = new ArrayList<>();
        for (int eventId : eventIds) {
            try {
                Event event = eventService.getEventById(eventId);
                events.add(event);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return events;
    }

    public TransactionTickets processTransaction(Transaction transaction) {
        // EventID retrieve for event details
        // Customer ID //retrieve balance
        // should also check for existing purchased tickets
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
            throw new IllegalArgumentException("Purchase must be made within 6 months in advance and at least 24 hours before the booking time.");
        }

        // check2 for qty limit
        if (qtyPurchased > 5 || qtyPurchased <= 0) {
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
            throw new IllegalArgumentException("Purchase limit is 5. you currently have " + ticketCount + " active tickets");
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

        // send email
        String email = customer.getEmail();
        String body = "";
        List<Ticket> tickets = transactionTickets.getTickets(); // ticketId, eventname, date, time, status
        for (int i = 0; i < tickets.size(); i++) {
            Ticket currTicket = tickets.get(i);
            String tempStr = String.format("Ticket %d: [TicketId: %d, EventName: %s, Date: %s, Time: %s, Status: %s]%n", i + 1, currTicket.getTicketId(), event.getEventName(), formatDate(event.getDate()), event.getTime(), currTicket.getStatus());
            body += tempStr;
        }

        emailService.sendEmail(email, body);
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
            throw new IllegalArgumentException("Cancellation Error: cancellation must be made 48 hours before Event start time");
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
        LocalDateTime eventStartTime = LocalDateTime.of(Integer.parseInt(tempDate[0]), Integer.parseInt(tempDate[1]), Integer.parseInt(tempDate[2]), Integer.parseInt(tempTime[0]), Integer.parseInt(tempTime[1]));

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
        LocalDateTime eventStartTime = LocalDateTime.of(Integer.parseInt(tempDate[0]), Integer.parseInt(tempDate[1]), Integer.parseInt(tempDate[2]), Integer.parseInt(tempTime[0]), Integer.parseInt(tempTime[1]));

        // Calculate minmium cancel time
        LocalDateTime minCancelTime = eventStartTime.minusHours(48);
        // returns true is cancel time is after the min cancel time
        return !cancelTime.isAfter(minCancelTime);
    }

    // date formatter
    public String formatDate(String date) {
        String retStr = "";
        String[] dateArr = date.split("-");
        for (int i = dateArr.length - 1; i >= 0; i--) {
            String currString = dateArr[i];
            if (i == 0) {
                retStr += currString;
            } else {
                retStr += currString + "-";
            }
        }
        return retStr;
    }
}
