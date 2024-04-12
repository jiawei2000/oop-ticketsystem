package oop.ticketing_system.controllers;

import oop.ticketing_system.models.Ticket;
import oop.ticketing_system.models.Transaction;
import oop.ticketing_system.models.TransactionTickets;
import oop.ticketing_system.services.TicketOfficerService;
import oop.ticketing_system.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ticketofficer")

public class TicketOfficerController {
    @Autowired
    private TicketOfficerService ticketOfficerService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/purchaseTicket")
    public ResponseEntity<?> purchasePhysicalTicket(@RequestBody Transaction transaction) {
        try {
            TransactionTickets response = ticketOfficerService.processPurchaseTicket(transaction);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getTransactionsByEventId/{eventId}")
    public ResponseEntity<?> getTransactionByEventId(@PathVariable int eventId) {
        try {
            List<TransactionTickets> response = transactionService.getTransactionByEventId(eventId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/tickets/{transactionId}")
    public ResponseEntity<?> getTicketsByTransactionId(@PathVariable int transactionId) {
        try {
            List<Ticket> response = transactionService.getTicketsByTransactionId(transactionId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
