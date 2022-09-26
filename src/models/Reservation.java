package models;

import java.util.Date;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkIn;
    private Date checkOut;

    public Reservation (Customer customer, IRoom room, Date checkIn, Date checkOut) {
        this.customer = customer;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public Customer getCustomer() {return customer;}

    public void setCustomer(Customer customer) {this.customer = customer;}

    public IRoom getRoom() {return room;}

    public void setRoom(IRoom room) {this.room = room;}

    public Date getCheckIn() {return checkIn;}

    public void setCheckIn(Date checkIn) {this.checkIn = checkIn;}

    public Date getCheckOut() {return checkOut;}

    public void setCheckOut(Date checkOut) {this.checkOut = checkOut;}

    @Override
    public String toString() {
        return "Reservation for " + customer
                + ", " + room + ", From: " + checkIn +
                " To: " + checkOut + ".";
    }
}
