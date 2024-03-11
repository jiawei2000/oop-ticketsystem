
public class TestDB {
    public static void main(String[] args) {
        EventDAO events = new EventDAO();
        System.out.println(events.getEventDAO().get(0));

    } 
}