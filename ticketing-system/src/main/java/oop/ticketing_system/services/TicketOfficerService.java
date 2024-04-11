package oop.ticketing_system.services;

import oop.ticketing_system.models.TicketOfficer;
import oop.ticketing_system.models.Transaction;
import oop.ticketing_system.models.TransactionTickets;
// import oop.ticketing_system.repository.EventRepository;
import oop.ticketing_system.repository.TicketOfficerRepository;
// import oop.ticketing_system.repository.TicketRepository;
// import oop.ticketing_system.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.HashMap;
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

    public Map<String, Object> removeTicketOfficer(int ticketOfficerId){
        TicketOfficer ticketOfficer = ticketOfficerRepository.getReferenceById(ticketOfficerId);
        String username = ticketOfficer.getUserName();
        Map<String, Object> data = new HashMap<>();
        data.put("username",username);
        
        ticketOfficerRepository.deleteById(ticketOfficerId);
        boolean existsAfterDeletion = ticketOfficerRepository.existsById(ticketOfficerId);
        if(existsAfterDeletion){
            data.put("code", 400);
            return data;
        }
        data.put("code", 200);
        return data;
    }

}
