package oop.ticketing_system.services;

import oop.ticketing_system.models.Event;
import oop.ticketing_system.models.EventReport;
import oop.ticketing_system.models.Ticket;
import oop.ticketing_system.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TicketService ticketService;

    public Event createEvent(Event event) {
        event.setStatus("Active");
        return eventRepository.save(event);
    }

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getEventsByManagerId(int userId) {
        return eventRepository.findByManagerId(userId);
    }

    public List<Event> getEventsByStatus(String status) {
        return eventRepository.findByStatus(status);
    }

    public Event getEventById(int eventId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        Event event = optionalEvent.orElse(null);
        if (event == null) {
            throw new IllegalArgumentException("Event Id " + eventId + " not found.");
        }
        return event;
    }

    public Event updateEventCancellationFee(int eventId, double newFee) {
        Event event = eventRepository.getReferenceById(eventId);
        if (newFee > event.getPrice()) {
            // The new cancellation fee, set at $50, cannot exceed the ticket price of $40.
            throw new IllegalArgumentException(
                    String.format("The new cancellation fee, set at $%.2f, cannot exceed the ticket price of $%.2f.",
                            newFee, event.getPrice()));
        }
        event.setCancellationFee(newFee);
        return eventRepository.save(event);
    }

    public Event cancelEvent(int eventId) {
        Event event = eventRepository.getReferenceById(eventId);
        if (!event.getStatus().equals("Active")) {
            throw new IllegalArgumentException("EventId: " + eventId + " is not 'Active'.");
        }
        List<Ticket> tickets = ticketService.updateTicketStatusByEventId(eventId, "Cancelled");
        transactionService.updateTransactionStatusByEventId(eventId, "Cancelled");
        // Handle Refund
        double refundAmount = event.getPrice() - event.getCancellationFee();
        transactionService.refundUsersByEventId(eventId, refundAmount);
        event.setStatus("Cancelled");
        return eventRepository.save(event);
    }

    public EventReport getEventStatistics(int eventId) {
        EventReport report = new EventReport();
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        Event event = optionalEvent.orElse(null);
        if (event == null) {
            throw new IllegalArgumentException("Event Id " + eventId + " not found.");
        }
        report.setEvent(event);
        // Tickets sold
        int sales = ticketService.getTicketsSoldByEventId(eventId);
        report.setSales(sales);
        // No refunds
        int noRefunds = ticketService.getTicketsRefundedByEventId(eventId);
        report.setNoRefunds(noRefunds);
        // No attended
        int attendance = (ticketService.getTicketsAttendedByEventId(eventId));
        report.setAttendance(attendance);
        // No Cancelled
        int noCancelled = ticketService.getTicketsCancelledByEventId(eventId);
        report.setNoCancelled(noCancelled);

        // Revenue
        double salesRevenue = event.getPrice() * sales;
        double cancellationRevenue = event.getCancellationFee() * noRefunds;
        double totalRevenue = salesRevenue + cancellationRevenue;
        report.setSaleRevenue(salesRevenue);
        report.setRefundRevenue(cancellationRevenue);
        report.setTotalRevenue(totalRevenue);
        return report;
    }

    public List<EventReport> getAllEventStatistics() {
        List<Event> eventList = getEvents();
        List<EventReport> eventReportList = new ArrayList<>();
        for (Event event : eventList) {
            EventReport eventReport = getEventStatistics(event.getEventId());
            eventReportList.add(eventReport);
        }
        return eventReportList;
    }

}
