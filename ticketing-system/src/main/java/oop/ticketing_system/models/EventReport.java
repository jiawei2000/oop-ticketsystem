package oop.ticketing_system.models;

public class EventReport {
    private Event event;
    private double totalRevenue;
    private double saleRevenue;
    private double refundRevenue;
    private int sales;
    private int attendance;
    private int noRefunds;
    private int noCancelled;

    public EventReport() {
    }

    // getters

    public Event getEvent() {
        return event;
    }

    public double getTotalRevenue() {
        return this.totalRevenue;
    }

    public double getSaleRevenue() {
        return this.saleRevenue;
    }

    public double getRefundRevenue() {
        return this.refundRevenue;
    }

    public int getSales() {
        return this.sales;
    }

    public int getAttendance() {
        return this.attendance;
    }

    public int getNoRefunds() {
        return this.noRefunds;
    }

    public int getNoCancelled() {
        return this.noCancelled;
    }

    // setters
    public void setEvent(Event event) {
        this.event = event;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public void setSaleRevenue(double saleRevenue) {
        this.saleRevenue = saleRevenue;
    }

    public void setRefundRevenue(double refundRevenue) {
        this.refundRevenue = refundRevenue;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public void setNoRefunds(int noRefunds) {
        this.noRefunds = noRefunds;
    }

    public void setNoCancelled(int noCancelled) {
        this.noCancelled = noCancelled;
    }
}
