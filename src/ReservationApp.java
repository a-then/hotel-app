
import models.IRoom;
import models.Room;
import api.HotelResource;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static models.RoomType.DOUBLE;
import static models.RoomType.SINGLE;
import static api.AdminResource.getInstance;

public class ReservationApp {
    public static void main(String[] args) {
        loadTestData();
        try (Scanner scanner = new Scanner(System.in)) {
            MainMenu.scanMainMenu(scanner);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    private static void loadTestData() {

        List<IRoom> rooms = new ArrayList<>();
        rooms.add(new Room("500", 100.00, SINGLE));
        rooms.add(new Room("501", 100.00, SINGLE));
        rooms.add(new Room("502", 80.00, DOUBLE));
        rooms.add(new Room("503", 85.00, DOUBLE));
        rooms.add(new Room("504", 90.00, DOUBLE));
        getInstance().addRoom(rooms);

        HotelResource.getInstance().createACustomer("ana", "then", "ana@ghu.com");
        HotelResource.getInstance().createACustomer("cecilia", "then", "ceci@ghu.com");
        HotelResource.getInstance().createACustomer("omar", "rod", "rod@ghu.com");

    }
}
