package oop.ticketing_system.services;
import oop.ticketing_system.models.*;
import oop.ticketing_system.repository.CustomerRepository;
import oop.ticketing_system.repository.ManagerRepository;
import oop.ticketing_system.repository.TicketOfficerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Customer authenticateCustomer(String username, String password){
        List<Customer> customers = customerRepository.findByUserName(username);
        if (!customers.isEmpty()){
            for(Customer customer: customers){
                if(customer.getPassword().equals(password)){
                    return customer;
                }
            }
        }
        return null;
    }

    public boolean authenticateManager(String username, String password){
        List<Manager> managers = managerRepository.findByUserName(username);
        if (!managers.isEmpty()){
            for(Manager manager: managers){
                if(manager.getPassword().equals(password)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean authenticateTicketOfficer(String username, String password){
        List<TicketOfficer> ticketOfficers = tickerOfficerRepository.findByUserName(username);
        if (!ticketOfficers.isEmpty()){
            for(TicketOfficer ticketOfficer: ticketOfficers){
                if(ticketOfficer.getPassword().equals(password)){
                    return true;
                }
            }
        }
        return false;
    }
    

}
