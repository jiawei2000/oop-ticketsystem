package oop.ticketing_system.models;

import jakarta.persistence.*;
@Entity
@Table(name="event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventID;
    private int userID;
    private String eventName;
    private String venue;
    private String date;
    private String time;
    private double price;
    private int stock;
    private double cancellationFee;

    public Event(){

    }
    public Event(int eventID, int userID, String eventName, String venue, String date, String time, double price,
            int stock, double cFee) {
        this.eventID = eventID;
        this.userID = userID;
        this.eventName = eventName;
        this.venue = venue;
        this.date = date;
        this.time = time;
        this.price = price;
        this.stock = stock;
        this.cancellationFee = cFee;
    }

    public String toString() {
        return String.format(
                "EventID: %d, UserID: %d, Event Name: %s, Venue: %s, Date: %s, Time: %s, Price: %.2f, Stock: %d, CancellationFee: %.2f",
                eventID, userID, eventName, venue, date, time, price, stock, cancellationFee);
    }

    // getters
    public int getEventID() {
        return this.eventID;
    }

    public int getUserID() {
        return this.userID;
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

}
