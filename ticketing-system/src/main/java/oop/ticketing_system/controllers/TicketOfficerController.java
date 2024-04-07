package oop.ticketing_system.controllers;

import oop.ticketing_system.models.Transaction;
import oop.ticketing_system.services.TicketOfficerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ticketofficer")

public class TicketOfficerController {
    @Autowired
    private TicketOfficerService ticketOfficerService;

    @PostMapping("/purchaseTicket")
    public ResponseEntity<?> createEvent(@RequestBody Transaction transaction) {
        try {
            Transaction response = ticketOfficerService.processPurchaseTicket(transaction);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
