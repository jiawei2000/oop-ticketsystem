package oop.ticketing_system.controllers;

import oop.ticketing_system.models.Ticket;
import oop.ticketing_system.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping(value = "/qrcode/{ticketId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getTicketQRCode(@PathVariable int ticketId) {
        try {
            BufferedImage response = ticketService.generateTicketBarcode(ticketId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyTicket(@RequestBody Map<String, Object> body) {
        String serial = (String) body.get("serial");
        int eventId = Integer.parseInt((String) body.get("eventId"));
        try {
            Map<String, Object> response = ticketService.verifyTicketSerial(serial, eventId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }

    @PutMapping("/updateTicketStatusToUsed/{ticketId}")
    public ResponseEntity<?> updateTicketStatusToUsed(@PathVariable int ticketId) {
        Ticket ticket = ticketService.updateTicketStatusToUsed(ticketId);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

}
