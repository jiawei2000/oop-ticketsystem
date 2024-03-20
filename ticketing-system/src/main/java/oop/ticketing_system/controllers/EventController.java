package oop.ticketing_system.controllers;

import oop.ticketing_system.models.Event;
import oop.ticketing_system.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/events")

public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<?> getEvent() {
        try {
            List<Event> event = eventService.getEvents();
            return new ResponseEntity<>(event, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        try {
            Event response = eventService.createEvent(event);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable int id) {
        try {
            Event response = eventService.getEventById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
//
//    @PutMapping("/{id}")
//    public Event updateUser(@PathVariable int id, @RequestBody Event event) {
//        return eventService.updateEvent(id, event);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteEvent(@PathVariable Long id) {
//        eventService.deleteEvent(id);
//    }


}


