public class TicketOfficer extends User{
    private int eventID;
    
    public TicketOfficer(int userID, String userName, String password, String type, int eventID){
        super(userID, userName, password, type);
        this.eventID = eventID;
    }

    public int getEventID(){
        return this.eventID;
    }

    public String toString(){
        return super.toString() + ", EventID: " + eventID;
    }
    
}
