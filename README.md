# Hotel Reservation Application 
## _Project 4_

The hotel reservation application is a java program to allow customers to find and book a hotel room based on room availability. 

## Components

- CLI for the User Interface.
- Java code to create and manage business logic
- Java collections for in-memory storage of the data needed
- Layering to support modularization and decoupling
 
### User Scenarios:
- Creating a customer account. The user needs to first create a customer account before they can create a reservation.

- Searching for rooms. The app should allow the user to search for available rooms based on provided checkin and checkout dates. If the application has available rooms for the specified date range, a list of the corresponding rooms will be displayed to the user for choosing.

- Booking a room. Once the user has chosen a room, the app will allow them to book the room and create a reservation, avoiding conflicting reservations.

- Viewing reservations. After booking a room, the app allows customers to view a list of all their reservations.

### Admin Scenarios
- Displaying all customers accounts.
- Viewing all of the rooms in the hotel.
- Viewing all of the hotel reservations.
- Adding a new room to the hotel application.

The hotel reservation application handles all exceptions gracefully (user inputs included), meaning:
- No crashing. The application does not crash based on user input.
- No unhandled exceptions. The app has try and catch blocks that are used to capture exceptions and provide useful information to the user. There are no unhandled exceptions.

## Installation
1. Download files
2. Open project with java IDE. 
3. Update JDK and Import necessary classes.
4. Run main class: 
```sh
ReservationApp.java
```

## Development
Pull requests are welcome. Please make sure to update tests as appropriate.

> Note: This is my first java project. If you notice something needs to be done, please open an issue to discuss what you would like to change, and give me a chance to work on it too. 


## Author
_@a-then_

## License
No license??
