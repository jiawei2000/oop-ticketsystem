package oop.ticketing_system.repository;

import oop.ticketing_system.models.Ticket;
import oop.ticketing_system.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    public List<Ticket> findByUserId(int userId);

    public List<Ticket> findByEventId(int eventId);

    public List<Ticket> findByEventIdAndStatus(int eventId, String status);

}

