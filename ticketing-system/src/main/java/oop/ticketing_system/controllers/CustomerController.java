package oop.ticketing_system.controllers;

import oop.ticketing_system.models.*;
import oop.ticketing_system.services.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.ErrorResponse;
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

    @GetMapping
    public List<Event> displayAllEvents() {
        return customerService.displayEvents();
    }

    @GetMapping("/transactionHistory/{customerId}")
    public ResponseEntity<List<Transaction>> displayUserTransactions(@PathVariable int customerId) {
        List<Transaction> transactions = customerService.displayTransactions(customerId);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/purchasedTickets/{customerId}")
    public List<Ticket> displayPurchasedTickets(@PathVariable int customerId) {
        return customerService.displayPurchasedTickets(customerId);
    }

    @PostMapping("/transaction")
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {

        try {
            Transaction temp = customerService.processTransaction(transaction);
            return new ResponseEntity<>(temp, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/purchasedTickets/{ticketId}")
    public  ResponseEntity<String> cancelTicket(@PathVariable int ticketId){
        try {
            customerService.processTicketCancellation(ticketId);
            return new ResponseEntity<>("Ticket cancelled successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    };

}
