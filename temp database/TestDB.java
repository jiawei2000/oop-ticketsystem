import java.util.*;

public class TestDB {
    public static void main(String[] args) {
        EventDAO events = new EventDAO();
        events.insertEvent(new Event(3, 1, "Twice Concert", "Stadium", "2023-04-09", "15:00:00", 300, 400, 0));

    }
}