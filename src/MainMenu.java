import models.Customer;
import models.IRoom;
import models.Reservation;
import api.AdminResource;
import api.HotelResource;

import java.text.*;
import java.util.*;

/**
 * CLI main menu and features
 */
public class MainMenu {
    public static final String mainDecor = "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
    public static final String subDecor = "_______________________________________________________________\n";

    public static void showMainMenu() {

        System.out.println(mainDecor);
        System.out.println("HOTEL RESERVATION");
        System.out.println(mainDecor);
        System.out.println(
                """
                        1. Find and reserve a room
                        2. See my reservations
                        3. Create an account
                        4. Admin
                        5. Exit""".indent(4));
        System.out.println(mainDecor);
        System.out.println("Choose an option by typing the corresponding number (1-5)");

    }

    /**
     * Shows main menu and takes user input to implement corresponding methods
     *
     * @param scanner scans for user input
     * @throws ParseException parse string as int
     */
    public static void scanMainMenu(Scanner scanner) throws ParseException {
        showMainMenu();
        int selection = 1;
        while (selection != 5) {
            try {
                selection = Integer.parseInt(scanner.next());

                switch (selection) {
                    case 1: {
                        System.out.println(subDecor + "Find and reserve a room");
                        findAvailRoom();
                        showMainMenu();
                        break;
                    }
                    case 2: {
                        System.out.println("See my reservations");
                        customerReservations();
                        showMainMenu();
                        break;
                    }
                    case 3: {
                        System.out.println("Create an account");
                        createAccount();
                        showMainMenu();
                        break;
                    }
                    case 4: {
                        System.out.println("Going to Administration Menu");
                        AdminMenu.showAdminMenu(scanner);
                        break;
                    }
                    case 5: {
                        System.out.println(subDecor);
                        System.out.println(mainDecor);
                        System.out.println("Goodbye! See you next time!");
                        System.out.println(subDecor);
                        System.out.println(mainDecor);
                        break;
                    }
                    default: {
                        System.out.println(selection + " is not an option. Enter a number 1-5");
                        showMainMenu();
                        break;
                    }
                }
            } catch (NumberFormatException numErr) {
                System.out.println("Invalid input. Please enter a number 1-5");
            }
        }
    }

    /**
     * Find available rooms during User desired period.
     * method catches int format
     * @throws ParseException - handles date format
     */
    private static void findAvailRoom() throws ParseException {
//getting stay dates
        try {
            Scanner scanner = new Scanner(System.in);

            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            System.out.println("Add Check-In date: (mm/dd/yyyy)");
            Date checkIn = format.parse(scanner.nextLine());
            System.out.println("Add Check-Out date: (mm/dd/yyyy)");
            Date checkOut = format.parse(scanner.nextLine());

            String roomNum;
            IRoom chosenRoom;

            try {
                boolean isDatesValid = datesValid(checkIn, checkOut);
                Collection<IRoom> availRooms = HotelResource.getInstance().findARoom(checkIn, checkOut);

                if (isDatesValid) {
                    if (!availRooms.isEmpty()) {
                        System.out.println(subDecor);
                        System.out.println("List of available rooms\n");
                        System.out.println(availRooms);
                        System.out.println("Type the number of the room you'd like to reserve. Make sure is a 3 digit number from list above.");
                        roomNum = String.valueOf(Integer.parseInt(scanner.next()));
                        chosenRoom = HotelResource.getInstance().getRoom(roomNum); //Sets String roomNum to an IRoom chosenRoom
                        if (!availRooms.contains(chosenRoom)) {
                            System.out.println("That room is not on the list, try a room number from the list.");
                            roomNum = String.valueOf(Integer.parseInt(scanner.next()));
                            }
                        reserveRoom(roomNum, checkIn, checkOut);
                    } else {
                        findAltRoom(checkIn, checkOut);
                    }

                } else {
                    System.out.println("""
                            Invalid Check-In or Check-Out date.
                            Check your dates to be (mm/dd/yyyy) and try again.
                            """);
                    System.out.println(subDecor);
                    findAvailRoom();
                }
            } catch (NumberFormatException roomErr) {
                System.out.println("""
                        Room number is a 3 digits number, ie: 503
                        Please, check and try again.
                        """);
            }

        } catch (IllegalArgumentException | ParseException e) {
            System.out.println("Dates have to follow the format mm/dd/yyyy");
            System.out.println(subDecor);
            findAvailRoom();
        }
    }

    /**
     * takes checkIn and checkOut from user to find alt dates a week later
     * @param in input from user  check in date
     * @param out input from user check out date
     * @throws ParseException string to int
     */
    private static void findAltRoom (Date in, Date out) throws ParseException {
        Scanner scan = new Scanner(System.in);

        Date altCheckIn = add7DaysToDate(in);
        Date altCheckOut = add7DaysToDate(out);
        Collection<IRoom> altAvailRooms = HotelResource.getInstance().findAltRoom(altCheckIn, altCheckOut);
        if (!altAvailRooms.isEmpty()) {
            System.out.println(altAvailRooms);
            System.out.println(subDecor);
            System.out.println("Choose a room by typing the room number.");
            String roomNum = String.valueOf(Integer.parseInt(scan.next()));
            IRoom chosenRoom = HotelResource.getInstance().getRoom(roomNum);
            if (!altAvailRooms.contains(chosenRoom)) {
                System.out.println("That room is not on the list, try a room number from the list.");
                roomNum = String.valueOf(Integer.parseInt(scan.next()));
            }
            reserveRoom(roomNum, altCheckIn, altCheckOut);
        } else {
            System.out.println("We seem to be busy these days... Please, try again with different dates");
            scanMainMenu(scan);
        }


    }

    /**
     * Takes room and dates to create a new reservation
     * @param roomNum input from user: number from list of rooms
     * @param in input from user: date
     * @param out input from user: date
     */
    private static void reserveRoom(String roomNum, Date in, Date out) {
        // checking for account
        try {
            IRoom chosenRoom = HotelResource.getInstance().getRoom(roomNum); //Sets String roomNum to an IRoom chosenRoom

            Scanner scan = new Scanner(System.in);
            System.out.println("To complete reservation of room " + roomNum + " you need an account with us. Do you have an account? Y o N");

            String hasAccount = scan.next();
            if (hasAccount.equalsIgnoreCase("y")) {
                System.out.println("What is the email associated to your account? (hello@me.com)");
                String email = scan.next();
                //TODO - validate email
                Customer cust = HotelResource.getInstance().getCustomer(email);
                if (cust != null) {
                    System.out.println("We found " + cust);
                    Reservation reservation = HotelResource.getInstance().bookARoom(email, chosenRoom, in, out);
                    System.out.println(cust.getFirstName().toUpperCase() + ",\n" + "you have successfully reserved a room! See details below");
                    System.out.println(reservation);
                } else {
                    System.out.println("Email not found. Please, start over and try a different email or create a new account first.");
                    scanMainMenu(scan);
                }

            } else if (hasAccount.equalsIgnoreCase("n")) {
                System.out.println("No problem! Let's create a new account");
                Customer newCustomer = createAccount();
                Reservation reservation = HotelResource.getInstance().bookARoom(newCustomer.getEmail(), chosenRoom, in, out);
                System.out.println(newCustomer.getFirstName().toUpperCase() + ", you have successfully reserved a room! See details below");
                System.out.println(reservation);
            } else {
                System.out.println("Only Y or N are valid inputs at this point. Please, try again!");
                scanMainMenu(scan);
            }
        } catch (NullPointerException emailNull) {
            System.out.println("We couldn't find an account with that email. Please, try again.");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Show a customers reservation using email
     */
    private static void customerReservations () {
            try {
                Scanner scan = new Scanner(System.in);
                System.out.println("Type in email to see your reservations. (hi@me.com");
                String email = scan.nextLine();
                Collection<Reservation> custRes = HotelResource.getInstance().getCustomersReservations(email);
                if (custRes.isEmpty()) {
                    System.out.println("We didn't find reservations associated to the email provided");
                } else {
                    System.out.println(subDecor);
                    System.out.println(subDecor);
                    System.out.println(custRes);
                }
            } catch (NullPointerException err) {
                System.out.println("Invalid email. Please check, and try again.");
            }

        }

    /**
     * Gets full name and email from user and adds new customer to customersList
     * @return customer
     */
    private static Customer createAccount () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("First Name:");
        String firstName = scanner.nextLine();
        System.out.println("Last Name:");
        String lastName = scanner.nextLine();
        System.out.println("Email (hi@hello.com:");
        String email = scanner.nextLine();
        try {
            Collection<Customer> allCustomers = AdminResource.getInstance().getAllCustomers();
            Customer cust = AdminResource.getInstance().getCustomer(email);
            if (allCustomers.contains(cust)) {
                System.out.println("Email already exists. Please try again");
                showMainMenu();
            } else {
                HotelResource.getInstance().createACustomer(firstName, lastName, email);
                System.out.println("Customer created successfully! Now you can book a room and see your future reservations.");
            }
        } catch (IllegalArgumentException emailError) {
            System.out.println(emailError.getLocalizedMessage());
            createAccount();
        }
        return HotelResource.getInstance().getCustomer(email);
    }

    // HELPERS
    private static Date add7DaysToDate (Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 7);
        return c.getTime();
    }
    private static boolean datesValid (Date checkIn, Date checkOut){
        //TODO - validate date format as well
        return checkIn.before(checkOut) && !checkIn.before(new Date()) && !checkOut.before(new Date());
    }

}



