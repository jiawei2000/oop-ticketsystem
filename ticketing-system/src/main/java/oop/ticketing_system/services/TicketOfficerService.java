package oop.ticketing_system.services;

import oop.ticketing_system.models.Transaction;
import oop.ticketing_system.models.TransactionTickets;
import oop.ticketing_system.repository.EventRepository;
import oop.ticketing_system.repository.TicketRepository;
import oop.ticketing_system.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class TicketOfficerService {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventRepository eventRepository;

    public TransactionTickets processPurchaseTicket(Transaction transaction) {
        return transactionService.purchaseTicket(transaction, "Physical");
    }

}
