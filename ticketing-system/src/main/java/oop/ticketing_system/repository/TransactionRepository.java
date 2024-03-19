package oop.ticketing_system.repository;

import oop.ticketing_system.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    public List<Transaction> findbyUser_Id(int userId);
}
