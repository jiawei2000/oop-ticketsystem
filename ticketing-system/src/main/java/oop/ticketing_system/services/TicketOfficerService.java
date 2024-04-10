package oop.ticketing_system.services;

import oop.ticketing_system.models.TicketOfficer;
import oop.ticketing_system.models.Transaction;
import oop.ticketing_system.models.TransactionTickets;
import oop.ticketing_system.repository.EventRepository;
import oop.ticketing_system.repository.TicketOfficerRepository;
import oop.ticketing_system.repository.TicketRepository;
import oop.ticketing_system.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TicketOfficerService {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TicketOfficerRepository ticketOfficerRepository;

    public TransactionTickets processPurchaseTicket(Transaction transaction) {
        return transactionService.purchaseTicket(transaction, "Physical");
    }

    public List<TicketOfficer> getTicketOfficerListByEventId(int eventId) {
        return ticketOfficerRepository.findByEventId(eventId);
    }

}
