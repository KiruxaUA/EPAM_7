package ua.epam6.IOCRUD.exceptions;

public class NoSuchEntryException extends Exception {
    public NoSuchEntryException() {
        super();
    }
    public NoSuchEntryException(String message) {
        super(message);
    }
}
