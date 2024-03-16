package oop.ticketing_system.repository;

import oop.ticketing_system.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
