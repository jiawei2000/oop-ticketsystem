package oop.ticketing_system.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventId;
    private int managerId;
    private String eventName;
    private String venue;
    private String date;
    private String time;
    private double price;
    private int stock;
    private double cancellationFee;
    private String status;

    public Event() {

    }

    public Event(int eventId, int managerId, String eventName, String venue, String date, String time, double price, int stock, double cFee, String status) {
        this.eventId = eventId;
        this.managerId = managerId;
        this.eventName = eventName;
        this.venue = venue;
        this.date = date;
        this.time = time;
        this.price = price;
        this.stock = stock;
        this.cancellationFee = cFee;
        this.status = status;
    }

    public String toString() {
        return String.format("EventId: %d, ManagerId: %d, Event Name: %s, Venue: %s, Date: %s, Time: %s, Price: %.2f, Stock: %d, CancellationFee: %.2f", eventId, managerId, eventName, venue, date, time, price, stock, cancellationFee);
    }

    // getters
    public int getEventId() {
        return this.eventId;
    }

    public int getManagerId() {
        return this.managerId;
    }

    public String getEventName() {
        return this.eventName;
    }

    public String getVenue() {
        return this.venue;
    }

    public String getDate() {
        return this.date;
    }

    public String getTime() {
        return this.time;
    }

    public double getPrice() {
        return this.price;
    }

    public double getCancellationFee() {
        return this.cancellationFee;
    }

    public int getStock() {
        return this.stock;
    }

    public String getStatus() {
        return this.status;
    }

    //setters
    public void setCancellationFee(double cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public void setStock(int num) {
        this.stock = num;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
