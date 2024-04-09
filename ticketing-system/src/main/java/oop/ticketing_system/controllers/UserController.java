package oop.ticketing_system.controllers;

import oop.ticketing_system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import oop.ticketing_system.models.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/users")

public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String getTest() {
        return "";
    }

    @PostMapping("/createManager")
    public ResponseEntity<?> createManager(@RequestBody Manager manager) {
        try {
            Manager response = userService.createManager(manager);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/createCustomer")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        System.out.println(customer);
        try {
            Customer response = userService.createCustomer(customer);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/createTicketOfficer")
    public ResponseEntity<?> createTicketOfficer(@RequestBody TicketOfficer ticketOfficer) {
        try {
            TicketOfficer response = userService.createTicketOfficer(ticketOfficer);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/loginCustomer")
    public ResponseEntity<?> loginCustomer(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("userName");
        String password = credentials.get("password");
        Customer response = userService.authenticateCustomer(username, password);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/loginManager")
    public ResponseEntity<String> loginManager(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("userName");
        String password = credentials.get("password");
        if (userService.authenticateManager(username, password)) {
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/loginTicketOfficer")
    public ResponseEntity<String> loginTicketOfficer(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("userName");
        String password = credentials.get("password");
        if (userService.authenticateTicketOfficer(username, password)) {
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST);
    }
}


