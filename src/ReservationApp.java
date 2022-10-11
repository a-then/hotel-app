import models.IRoom;
import models.Room;
import api.HotelResource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

        List<IRoom> rooms = new ArrayList<>();
        rooms.add(new Room("500", 100.00, SINGLE));
        rooms.add(new Room("501", 100.00, SINGLE));
        rooms.add(new Room("502", 80.00, DOUBLE));
        rooms.add(new Room("503", 85.00, DOUBLE));
        rooms.add(new Room("504", 90.00, DOUBLE));
        rooms.add(new Room("505", 00.00, SINGLE));
//        rooms.add(new Room("506", 85.00, DOUBLE));
//        rooms.add(new Room("507", 90.00, DOUBLE));
//        rooms.add(new Room("508", 00.00, SINGLE));
        getInstance().addRoom(rooms);

        HotelResource.getInstance().createACustomer("ana", "then", "a@t.com");
        HotelResource.getInstance().createACustomer("cecilia", "then", "c@t.com");
        HotelResource.getInstance().createACustomer("omar", "rod", "o@r.com");
        HotelResource.getInstance().createACustomer("maria", "then", "m@t.com");
        HotelResource.getInstance().createACustomer("jose", "then", "j@t.com");
        HotelResource.getInstance().createACustomer("roger", "rod", "r@r.com");
        HotelResource.getInstance().createACustomer("test1", "1", "t@1.com");
        HotelResource.getInstance().createACustomer("test2", "2", "t@2.com");

        try {
            HotelResource.getInstance().bookARoom("a@t.com", rooms.get(0), format.parse("10/05/2022"), format.parse("10/06/2022"));
            HotelResource.getInstance().bookARoom("c@t.com", rooms.get(1), format.parse("10/05/2022"), format.parse("10/06/2022"));
            HotelResource.getInstance().bookARoom("o@r.com", rooms.get(2), format.parse("10/05/2022"), format.parse("10/06/2022"));
            HotelResource.getInstance().bookARoom("m@t.com", rooms.get(3), format.parse("10/05/2022"), format.parse("10/06/2022"));
            HotelResource.getInstance().bookARoom("j@t.com", rooms.get(4), format.parse("10/05/2022"), format.parse("10/06/2022"));
            //HotelResource.getInstance().bookARoom("r@r.com", rooms.get(0), format.parse("10/10/2022"), format.parse("10/16/2022"));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
