package oop.ticketing_system.controllers;

import oop.ticketing_system.models.Event;
import oop.ticketing_system.models.EventReport;
import oop.ticketing_system.models.TicketOfficer;
import oop.ticketing_system.services.EventService;
import oop.ticketing_system.services.TicketOfficerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/manager")

public class ManagerController {
    @Autowired
    private EventService eventService;

    @Autowired
    private TicketOfficerService ticketOfficerService;

    @PostMapping("/createEvent")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event response = eventService.createEvent(event);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getEvents/{managerId}")
    public ResponseEntity<List<Event>> getEventsByManager(@PathVariable int managerId) {
        List<Event> events = eventService.getEventsByManagerId(managerId);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @PostMapping("/updateEventCancellationFee/{eventId}")
    public ResponseEntity<Event> updateEventCancellationFee(@PathVariable int eventId, @RequestBody Map<String, Object> body) {
        Event event = eventService.updateEventCancellationFee(eventId, (Double) body.get("newFee"));
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PutMapping("/cancelEvent/{eventId}")
    public ResponseEntity<?> cancelEvent(@PathVariable int eventId) {
        try {
            Event event = eventService.cancelEvent(eventId);
            return new ResponseEntity<>(event, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getEventStatistics/{eventId}")
    public ResponseEntity<?> getEventStatistics(@PathVariable int eventId) {
        try {
            EventReport report = eventService.getEventStatistics(eventId);
            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getEventStatistics")
    public ResponseEntity<?> getAllEventStatistics() {
        try {
            List<EventReport> reportList = eventService.getAllEventStatistics();
            return new ResponseEntity<>(reportList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getTicketOfficers/{eventId}")
    public ResponseEntity<?> getTicketOfficersByEventId(@PathVariable int eventId) {
        try {
            List<TicketOfficer> response = ticketOfficerService.getTicketOfficerListByEventId(eventId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/removeTicketOfficers/{ticketOfficerId}")
    public ResponseEntity<?> removeTicketOfficer(@PathVariable int ticketOfficerId){
        Map<String, Object> isRemove = ticketOfficerService.removeTicketOfficer(ticketOfficerId);
        //ticketofficerId and username
        int code = (Integer)isRemove.get("code");
        String username = (String)isRemove.get("username");

        if (code == 400){
            return new ResponseEntity<>(String.format("[TicketOfficerId: %d, Username: %s] removal was unsuccessful.", ticketOfficerId, username), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(String.format("[TicketOfficerId: %d, Username: %s] was successfully removed.", ticketOfficerId, username), HttpStatus.OK); 
    }
}