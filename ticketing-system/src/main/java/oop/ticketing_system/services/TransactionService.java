package oop.ticketing_system.services;

import oop.ticketing_system.models.Transaction;
import oop.ticketing_system.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    CustomerService customerService;

    public List<Transaction> updateTransactionStatusByEventId(int eventId, String newStatus) {
        List<Transaction> transactions = transactionRepository.findByEventIdAndStatus(eventId, "Active");
        for (Transaction transaction : transactions) {
            transaction.setStatus(newStatus);
        }
        return transactionRepository.saveAll(transactions);
    }

    public List<Transaction> refundUsersByEventId(int eventId, double amount) {
        List<Transaction> transactions = transactionRepository.findByEventIdAndStatus(eventId, "Cancelled");
        for (Transaction transaction : transactions) {
            customerService.addCustomerBalance(transaction.getUserId(), amount * transaction.getNumTicketPurchased());
        }
        return transactions;
    }

}
