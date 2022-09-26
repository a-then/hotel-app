import models.IRoom;
import models.Room;
import models.RoomType;
import api.AdminResource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static models.RoomType.SINGLE;

/**
 * CLI admin menu and features
 */
public class AdminMenu {
    public static final String mainDecor = "\n~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~@~\n";
    public static final String subDecor = "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";


    public static void adminMenu() {
        System.out.println(mainDecor);
        System.out.println("HOTEL RESERVATION");
        System.out.println(mainDecor);
        System.out.println(
                """
                        1. See all customers
                        2. See all rooms
                        3. See all reservations
                        4. Add a room
                        5. Back to Main Menu""".indent(4));
        System.out.println(subDecor);
        System.out.println("Choose an option by typing the corresponding number (1-5)");
    }
    public static void showAdminMenu (Scanner scanner) {
        adminMenu();
        int selection = 0;
        while (selection != 5 ) {

            try {
                selection = Integer.parseInt(scanner.next());

                switch (selection) {
                    case 1:
                        System.out.println(subDecor);
                        System.out.println("CUSTOMERS");
                        System.out.println(AdminResource.getInstance().getAllCustomers());
                        System.out.println(subDecor);
                        adminMenu();
                        break;
                    case 2:
                        System.out.println(subDecor);
                        System.out.println("ROOMS");
                        System.out.println(AdminResource.getInstance().getAllRooms());
                        System.out.println(subDecor);
                        adminMenu();
                        break;
                    case 3:
                        System.out.println(subDecor);
                        System.out.println("RESERVATIONS");
                        AdminResource.getInstance().displayAllReservations();
                        System.out.println(subDecor);
                        adminMenu();
                        break;
                    case 4:
                        System.out.println("To add a new room you'll need the room number, room price and room type.");
                        addNewRoom();
                        adminMenu();
                        break;
                    case 5:
                        System.out.println("Returning to Main Menu");
                        MainMenu.showMainMenu();
                        break;
                    default:
                        System.out.println("Invalid input. Enter a number 1-5");
                        adminMenu();
                }
            } catch (NumberFormatException | ParseException numErr) {
                System.out.println("Please enter a number 1-5");

            }
        }
    }

    private static void addNewRoom() throws ParseException {
        List<IRoom> rooms = new ArrayList<>();
        AdminResource.getInstance().addRoom(rooms);
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("What is the number of the new room? (3 digit number)");
            String roomNumber = String.valueOf(Integer.parseInt(scanner.next()));
            System.out.println("What is the price of this room? (00.00)");
            Double roomPrice = scanner.nextDouble();
            System.out.println("""
                    What type of room is this new room? (1 or 2)
                    1. SINGLE
                    2. DOUBLE
                    """);
            int typeOfRoom = scanner.nextInt();
            RoomType roomType = null;
            switch (typeOfRoom) {
                case 1:
                    roomType = SINGLE;
                    break;
                case 2:
                    roomType = RoomType.DOUBLE;
                    break;
                default:
                    System.out.println("""
                            Room type must be 1 for single rooms or 2 for double rooms.
                            Please, try again.
                            """);
                    System.out.println(subDecor);
                    addNewRoom();
            }
            System.out.println("You are trying to add Room " + roomNumber + ", Price $ "+
                                roomPrice + ", Type " + roomType +".\n" +
                                """
                                Type S to save and add new room.
                                Type C to cancel and start over.
                                """.indent(4));

            String roomCorrect = scanner.next();
            try {
                if (roomCorrect.equalsIgnoreCase("s")) {
                    rooms.add(new Room(roomNumber, roomPrice, roomType));
                    AdminResource.getInstance().addRoom(rooms);
                    System.out.println("Room " + roomNumber + " added successfully. " +
                            "It should now appear on list of rooms, and be available for reservation.");
                } else if (roomCorrect.equalsIgnoreCase("c")) {
                    System.out.println("Ok. Let's try that one more time!");
                    System.out.println(subDecor);
                    addNewRoom();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Type S or C to continue");

            }


        } catch (InputMismatchException notDouble) {
            System.out.println("""
                    Room price is a number with digits after decimal point. ie: 90.5
                    Please, check price and try again.
                    """);
            System.out.println(subDecor);
            addNewRoom();
        } catch (NumberFormatException roomErr) {
            System.out.println("""
                    Room number is a 3 digits number, ie: 503
                    Please, check and try again.
                    """);
            System.out.println(subDecor);
            addNewRoom();
        }


    }


}

