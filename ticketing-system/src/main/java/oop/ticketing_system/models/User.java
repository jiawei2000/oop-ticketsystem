package oop.ticketing_system.models;

import jakarta.persistence.*;

@MappedSuperclass
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String userName;
    private String pwd;
    private String type;

    public User() {
    }

    public User(int userId, String userName, String pwd, String type) {
        this.userId = userId;
        this.userName = userName;
        this.pwd = pwd;
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
        return this.pwd;
    }

    public String getUserType() {
        return this.type;
    }

    public String toString() {
        return String.format("UserID: %d, Username: %s, Password: %s, Type: %s", userId, userName, pwd, type);
    }

    //setters
    public void setType(String type) {
        this.type = type;
    }

}
