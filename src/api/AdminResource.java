package api;

import models.Customer;
import models.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class AdminResource {
    private AdminResource () {}
    private static final AdminResource adminResource = new AdminResource();
    public static AdminResource getInstance() { return adminResource;}

    public Customer getCustomer (String email) {
        return CustomerService.getInstance().getCustomer(email);
    }

    public void addRoom (List<IRoom> rooms) {
        for (IRoom r : rooms) {
            ReservationService.getInstance().addRoom(r);
        }
    }
    public Collection<IRoom> getAllRooms() {
        return ReservationService.getInstance().getAllRooms();
    }
    public Collection<Customer> getAllCustomers() {
        return CustomerService.getInstance().getAllCustomers();
    }
    public void displayAllReservations() {
        ReservationService.getInstance().printAllReservation();
    }

    public void addReservation(Customer customer, IRoom room, Date checkIn, Date checkOut) {
        ReservationService.getInstance().reserveARoom(customer, room, checkIn, checkOut);
    }
}
