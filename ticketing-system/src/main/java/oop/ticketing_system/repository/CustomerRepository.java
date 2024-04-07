package oop.ticketing_system.repository;

import oop.ticketing_system.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    public List<Customer> findByUserName(String userName);
}
