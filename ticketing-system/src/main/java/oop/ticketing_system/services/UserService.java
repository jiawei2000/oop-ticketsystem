package oop.ticketing_system.services;
import oop.ticketing_system.models.*;
import oop.ticketing_system.repository.CustomerRepository;
import oop.ticketing_system.repository.ManagerRepository;
import oop.ticketing_system.repository.TicketOfficerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private TicketOfficerRepository tickerOfficerRepository;
    
    
    public Customer createCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public Manager createManager(Manager manager){
        return managerRepository.save(manager);
    }

    public TicketOfficer createTicketOfficer(TicketOfficer ticketOfficer){
        return tickerOfficerRepository.save(ticketOfficer);
    }
    


}
