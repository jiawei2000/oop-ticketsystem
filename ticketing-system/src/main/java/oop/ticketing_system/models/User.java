package oop.ticketing_system.models;

import jakarta.persistence.*;

@MappedSuperclass
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String userName;
    private String password;
    private String type;

    public User() {
    }

    public User(int userId, String userName, String password, String type) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.type = type;
    }

    //getter
    public int getUserId() {
        return this.userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUserType() {
        return this.type;
    }

    public String toString() {
        return String.format("UserID: %d, Username: %s, Password: %s, Type: %s", userId, userName, password, type);
    }

    //setters
    public void setType(String type) {
        this.type = type;
    }

}
