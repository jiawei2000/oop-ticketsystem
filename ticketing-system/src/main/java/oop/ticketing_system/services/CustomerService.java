package oop.ticketing_system.services;

import oop.ticketing_system.models.*;
import oop.ticketing_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public List<Event> displayEvents(){
        return eventRepository.findAll();
    }

    public List<Transaction> displayTransactions(int customerId){
        return transactionRepository.findByUserId(customerId);
    }



}
