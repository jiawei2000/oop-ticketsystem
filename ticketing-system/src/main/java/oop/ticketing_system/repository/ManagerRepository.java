package oop.ticketing_system.repository;

import oop.ticketing_system.models.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    public List<Manager> findByUserName(String userName);
}
