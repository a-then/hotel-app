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
                        findAndReserveRoom();
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

    private static void customerReservations() {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("Type in email to see your reservations");
            String email = scan.nextLine();
            Collection<Reservation> custRes = HotelResource.getInstance().getCustomersReservations(email);
            if (custRes.isEmpty()) {
                System.out.println("We didn't find reservations associated to the email provided");
            } else {
                System.out.println(subDecor);
                System.out.println(subDecor);
                System.out.println(custRes);
            }
        } catch (NullPointerException err){
            System.out.println("Invalid email. Please check, and try again");
        }

    }

    private static void findAndReserveRoom() throws ParseException {
//getting stay dates
        try {
            Scanner scanner = new Scanner(System.in);

            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

            System.out.println("Add Check-In date: (mm/dd/yyyy)");
            Date checkIn = format.parse(scanner.nextLine());

            System.out.println("Add Check-Out date: (mm/dd/yyyy)");
            Date checkOut = format.parse(scanner.nextLine());

            try {
                boolean isDatesValid = datesValid(checkIn, checkOut);
                Collection<IRoom> availRooms = findAvailableRoom(checkIn, checkOut);

                if (isDatesValid) {
                    if (availRooms.isEmpty()) {
                    System.out.println("No rooms available for your dates. We'll look for the following week.");
                    Collection<IRoom> altAvailRooms = HotelResource.getInstance().findAltRoom(checkIn, checkOut);
                        if (altAvailRooms.isEmpty()) {
                        System.out.println("We seem to be busy these days... Please, try again with different dates");
                        scanMainMenu(scanner);
                        }
                    } else {
                        System.out.println("List of available rooms\n");
                        System.out.println(availRooms);
                        System.out.println("Type the number of the room you'd like to reserve");
                        String roomNum = String.valueOf(Integer.parseInt(scanner.next()));
                        IRoom chosenRoom = HotelResource.getInstance().getRoom(roomNum); //Sets String roomNum to an IRoom chosenRoom

                        while (!availRooms.contains(chosenRoom)) {
                            System.out.println("Wrong input, try a room number from the list above");
                            roomNum = String.valueOf(Integer.parseInt(scanner.next()));
                            chosenRoom = HotelResource.getInstance().getRoom(roomNum);
                        }

                        System.out.println("To reserve room " + roomNum + " you need an account with us. Do you have one? y/n");
                        // checking for account
                        String hasAccount = scanner.next();
                        if (hasAccount.equalsIgnoreCase("y")) {
                            System.out.println("What is the email associated to your account?");
                            String email = scanner.next();
                            //TODO - validate email
                            Customer cust = HotelResource.getInstance().getCustomer(email);
                            if (!cust.equals(null)) {
                                System.out.println("We found " + cust);
                                Reservation reservation = HotelResource.getInstance().bookARoom(email, chosenRoom, checkIn, checkOut);
                                System.out.println(cust.getFirstName().toUpperCase() + ", you have successfully reserved a room! See details below");
                                System.out.println(reservation);
                            } else {
                                System.out.println("Email not found. Please, try again with a different email or try creating a new account.");
                                findAndReserveRoom();
                            }

                        } else if (hasAccount.equalsIgnoreCase("n")) {
                            System.out.println("No problem! Let's create a new account");
                            Customer newCustomer = createAccount();
                            Reservation reservation = HotelResource.getInstance().bookARoom(newCustomer.getEmail(), chosenRoom, checkIn, checkOut);
                            System.out.println(newCustomer.getFirstName().toUpperCase() + ", you have successfully reserved a room! See details below");
                            System.out.println(reservation);
                        } else {
                            System.out.println("Only Y or N are valid inputs at this point. Please, try again!");
                            scanMainMenu(scanner);
                        }

                    }
                } else {
                    System.out.println("Invalid Check-In or Check-Out date. Check your dates to be (mm/dd/yyyy) and try again.");
                    System.out.println(subDecor);
                    findAndReserveRoom();
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
            findAndReserveRoom();
        } catch (NullPointerException emailNull) {
            System.out.println("We couldn't find an account with that email. Please, try again.");
        }
    }
    private static boolean datesValid(Date checkIn, Date checkOut) {
        //TODO - validate date format as well
        return checkIn.before(checkOut) && !checkIn.before(new Date()) && !checkOut.before(new Date());
    }

    private static Collection<IRoom> findAvailableRoom(Date checkIn, Date checkOut) {
        return HotelResource.getInstance().findARoom(checkIn, checkOut);
    }

    private static Customer createAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("First Name:");
        String firstName = scanner.nextLine();
        System.out.println("Last Name:");
        String lastName = scanner.nextLine();
        System.out.println("Email:");
        String email = scanner.nextLine();
        try {
            Collection<Customer> allCustomers = AdminResource.getInstance().getAllCustomers();
            if (email.equalsIgnoreCase(String.valueOf(allCustomers.contains(email)))) {
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
}



