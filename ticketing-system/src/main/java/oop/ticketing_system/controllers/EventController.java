package oop.ticketing_system.controllers;

import oop.ticketing_system.models.Event;
import oop.ticketing_system.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/events")

public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getTest() {
        return eventService.getEvents();
    }

    @PostMapping
    public Event createUser(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

//    @GetMapping("/{id}")
//    public Event getUserById(@PathVariable int id) {
//        return eventService.getEventById(id);
//    }
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


