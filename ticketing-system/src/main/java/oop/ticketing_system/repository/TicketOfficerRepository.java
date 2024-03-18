package oop.ticketing_system.repository;

import oop.ticketing_system.models.TicketOfficer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketOfficerRepository extends JpaRepository<TicketOfficer, Integer> {

}
