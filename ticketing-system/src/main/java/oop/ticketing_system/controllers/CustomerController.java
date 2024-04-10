package oop.ticketing_system.controllers;

import oop.ticketing_system.models.*;
import oop.ticketing_system.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private EventService eventService;

    //displays "Active" events
    @GetMapping("/displayEvents")
    public ResponseEntity<List<Event>> displayAllEvents() {
        return new ResponseEntity<>(eventService.getEventsByStatus("Active"), HttpStatus.OK);
    }

    //displays transaction history
    @GetMapping("/transactionHistory/{customerId}")
    public ResponseEntity<List<Transaction>> displayUserTransactions(@PathVariable int customerId) {
        List<Transaction> transactions = customerService.displayTransactions(customerId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/transactionDetailsHistory/{customerId}")
    public ResponseEntity<?> displayUserTransactionTickets(@PathVariable int customerId) {
        try {
            List<TransactionTickets> response = customerService.displayTransactionTickets(customerId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    //display purchased ticket
    @GetMapping("/purchasedTickets/{customerId}")
    public ResponseEntity<List<Ticket>> displayPurchasedTickets(@PathVariable int customerId) {
        List<Ticket> tickets = customerService.displayPurchasedTickets(customerId);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    //get customer Events 
    @GetMapping("/customerEvents/{customerId}")
    public ResponseEntity<List<Event>> displayCustomerEvents(@PathVariable int customerId) {
        List<Event> events = customerService.displayCustomerEvent(customerId);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    //create Transactions 
    @PostMapping("/transaction")
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {

        try {
            TransactionTickets temp = customerService.processTransaction(transaction);
            return new ResponseEntity<>(temp, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/cancelTransaction/{transactionId}")
    public ResponseEntity<?> cancelTicket(@PathVariable int transactionId) {
        try {
            Transaction trans = customerService.processTransactionCancellation(transactionId);
            return new ResponseEntity<>(trans, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
