public class Event {
    private int eventID;
    private int userID;
    private String eventName;
    private String venue;
    private String date;
    private String time;
    private double price;
    private int stock;

    public Event(int eventID, int userID, String eventName, String venue, String date, String time, double price,
            int stock) {
        this.eventID = eventID;
        this.userID = userID;
        this.eventName = eventName;
        this.venue = venue;
        this.date = date;
        this.time = time;
        this.price = price;
        this.stock = stock;
    }

    public String toString() {
        return String.format(
                "EventID: %d, UserID: %d, Event Name: %s, Venue: %s, Date: %s, Time: %s, Price: %.2f, Stock: %d",
                eventID, userID, eventName, venue, date, time, price, stock);
    }
}
