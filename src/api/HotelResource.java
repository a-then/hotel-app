package api;

import models.Customer;
import models.IRoom;
import models.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private HotelResource () {}
    private static final HotelResource hotelResource = new HotelResource();
    public static HotelResource getInstance() { return hotelResource;}

    public Customer getCustomer(String email) {
        return CustomerService.getInstance().getCustomer(email);
    }
    public void createACustomer(String firstName, String lastName, String email) {
        CustomerService.getInstance().addCustomer(firstName, lastName, email);
    }
    public IRoom getRoom (String roomNumber) {
        return ReservationService.getInstance().getARoom(roomNumber);
    }
    public Reservation bookARoom (String customerEmail, IRoom room, Date checkIn, Date checkOut) {
        if (getCustomer(customerEmail) == null){
            System.out.println("No customer associated with " + customerEmail);
        }
        return ReservationService.getInstance().reserveARoom(getCustomer(customerEmail),room,checkIn,checkOut);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        return ReservationService.getInstance().getCustomersReservation(CustomerService.getInstance().getCustomer(customerEmail));
    }

    public Collection<IRoom> findARoom (Date checkIn, Date checkOut) {
        return ReservationService.getInstance().findRooms(checkIn, checkOut);
    }
    public Collection<IRoom> findAltRoom (Date checkIn, Date checkOut) {
        return ReservationService.getInstance().findAltRooms(checkIn, checkOut);
    }

}
