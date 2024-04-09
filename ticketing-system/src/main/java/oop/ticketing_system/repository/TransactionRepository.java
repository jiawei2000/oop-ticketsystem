package oop.ticketing_system.repository;

import oop.ticketing_system.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    public List<Transaction> findByUserId(int userId);

    public List<Transaction> findByEventId(int eventId);

    public List<Transaction> findByEventIdAndStatus(int eventId, String status);

    public List<Transaction> findByEventIdAndUserId(int eventId, int userId);

    public List<Transaction> findByUserIdAndStatus(int userId, String status);
}
