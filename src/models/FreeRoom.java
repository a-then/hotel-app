package models;

public class FreeRoom extends Room {
    public FreeRoom(String roomNumber, double price, RoomType roomType) {
        super(roomNumber, price, roomType);
        this.roomNumber = roomNumber;
        this.price = 0.00;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "FreeRoom{" +
                "roomNumber='" + roomNumber + '\'' +
                ", price=" + price +
                ", roomType=" + roomType +
                '}';
    }
}
