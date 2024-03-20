package oop.ticketing_system.services;

import oop.ticketing_system.models.Event;
import oop.ticketing_system.models.Ticket;
import oop.ticketing_system.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TicketService ticketService;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getEventsByManagerId(int userId) {
        return eventRepository.findByManagerId(userId);
    }

    public Event updateEventCancellationFee(int eventId, double newFee) {
        Event event = eventRepository.getReferenceById(eventId);
        event.setCancellationFee(newFee);
        return eventRepository.save(event);
    }

    public Event cancelEvent(int eventId) {
        Event event = eventRepository.getReferenceById(eventId);
        if (!event.getStatus().equals("Active")) {
            throw new IllegalArgumentException("Event Id " + eventId + " is not Active");
        }
        List<Ticket> tickets = ticketService.updateTicketStatusByEventId(eventId, "Cancelled");
        transactionService.updateTransactionStatusByEventId(eventId, "Cancelled");
        //Handle Refund
        double refundAmount = event.getPrice() - event.getCancellationFee();
        transactionService.refundUsersByEventId(eventId, refundAmount);
        event.setStatus("Cancelled");
        return eventRepository.save(event);
    }

}
