package service;

import models.*;

import java.util.*;


public class ReservationService {
    private ReservationService () {}
    private static final ReservationService reservationService = new ReservationService();
    public static ReservationService getInstance() { return reservationService;}

    private final Map<String, IRoom> roomsList= new HashMap<>();
    private final Map<String, Reservation> reservationsList = new HashMap<>();

    public void addRoom(IRoom room) {
        roomsList.putIfAbsent(room.getRoomNumber(), room);
    }
    public IRoom getARoom(String roomNumber) {
        return roomsList.get(roomNumber);
    }
    public Collection<IRoom> getAllRooms() {
        return roomsList.values();
    }
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkIn, Date checkOut) {

        Reservation reservation = new Reservation(customer, room, checkIn, checkOut);
        reservationsList.put(customer.getEmail(), reservation);

        return reservationsList.get(customer.getEmail());
    }
    public Collection<IRoom> findRooms(Date checkIn, Date checkOut) {
        List<IRoom> availableRooms = new ArrayList<>();
        for (IRoom room : roomsList.values()) {
            if (isRoomAvailable(room, checkIn, checkOut)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }
     public boolean isRoomAvailable(IRoom room, Date checkIn, Date checkOut) {

         for (Reservation reservation : reservationsList.values()) {
            Room reserved = (Room) reservation.getRoom();
            if (reserved.getRoomNumber().equals(room.getRoomNumber()) && datesOverlap(checkIn, checkOut, reservation)) {
                    return false;
            }
        }
        return true;
    }
    public Collection<IRoom> findAltRooms(Date altCheckIn, Date altCheckOut) {
        System.out.println("There are no rooms available during this time.\n"+
                "We also checked from " + altCheckIn + " to " + altCheckOut + ", and...");
        return findRooms(altCheckIn, altCheckOut);

    }
    boolean datesOverlap(Date checkIn, Date checkOut, Reservation reservation) {
        return (checkIn.equals(reservation.getCheckIn()) && checkOut.equals(reservation.getCheckOut())
                ||checkIn.after(reservation.getCheckIn()) && checkOut.before(reservation.getCheckOut())
                || checkIn.before(reservation.getCheckIn()) && checkOut.after(reservation.getCheckIn())
                || reservation.getCheckIn().before(checkIn) && reservation.getCheckOut().after(checkIn)
                || reservation.getCheckIn().after(checkIn) && reservation.getCheckOut().before(checkOut));
    }

    public Collection<Reservation> getCustomersReservation (Customer customer) {
        List<Reservation> customersReservations = new ArrayList<>();
        for (Reservation reservation  : reservationsList.values()) {
            if (customer.equals(reservation.getCustomer())) {
                customersReservations.add(reservation);
            }
        }
        return customersReservations;
    }

    public void printAllReservation() {
        if (reservationsList.isEmpty()) {
            System.out.println("No reservations yet.");
        } else
            System.out.println(reservationsList.values());

    }
}
