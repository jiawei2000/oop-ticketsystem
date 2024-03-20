package oop.ticketing_system.repository;

import oop.ticketing_system.models.Event;
import oop.ticketing_system.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    public List<Event> findByManagerId(int managerId);


}
