package Exeptions;

public class InvalidRoomsCountException extends IllegalArgumentException {
    public InvalidRoomsCountException(String message) {
        super(message);
    }
}
