package oop.ticketing_system.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Manager")
public class Manager extends User {
    public Manager() {
        super();
        super.setType("Manager");
    }

}
