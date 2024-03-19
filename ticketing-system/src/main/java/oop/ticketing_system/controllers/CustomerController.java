package oop.ticketing_system.controllers;

import oop.ticketing_system.models.*;
import oop.ticketing_system.services.CustomerService;
// import oop.ticketing_system.services.EventService;

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

    @GetMapping
    public List<Event> displayAllEvents(){
        return customerService.displayEvents();
    }

    @GetMapping("/transaction/{customerId}")
    public ResponseEntity<List<Transaction>> displayUserTransaction(@PathVariable int customerId){
        List<Transaction> transactions = customerService.displayTransactions(customerId);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/purchasedticket/{customerId}")
    public List<Ticket> displayPurchasedTickets(@PathVariable int customerId){
        return customerService.displayPurchasedTickets(customerId);
    }
}
