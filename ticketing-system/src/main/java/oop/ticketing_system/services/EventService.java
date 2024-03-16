package oop.ticketing_system.services;

import oop.ticketing_system.models.Event;
import oop.ticketing_system.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }
}
