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
        try {
            Ticket response = ticketService.verifyTicketSerial(serial);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }

}
