package oop.ticketing_system.services;

import oop.ticketing_system.repository.CustomerRepository;
import oop.ticketing_system.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private CustomerRepository customerRepository;
    public String addCustomerBalance(int customerId, double amountToAdd) {
        return "Hello Worldss";
    }

}
