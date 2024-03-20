package oop.ticketing_system.services;

import oop.ticketing_system.models.Ticket;
import oop.ticketing_system.models.Transaction;
import oop.ticketing_system.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> updateTicketStatusByEventId(int eventId, String newStatus) {
        List<Ticket> tickets = ticketRepository.findByEventIdAndStatus(eventId, "Active");
        for (Ticket ticket : tickets) {
            ticket.setStatus(newStatus);
        }
        return ticketRepository.saveAll(tickets);
    }

    public int getTicketsSoldByEventId(int eventId) {
        return ticketRepository.countByEventIdAndStatus(eventId, "Active");
    }

    public int getTicketsRefundedByEventId(int eventId) {
        return ticketRepository.countByEventIdAndStatus(eventId, "Refunded");
    }

    public int getTicketsAttendedByEventId(int eventId) {
        return ticketRepository.countByEventIdAndStatus(eventId, "Used");
    }

    public int getTicketsCancelledByEventId(int eventId) {
        return ticketRepository.countByEventIdAndStatus(eventId, "Cancelled");
    }

}
