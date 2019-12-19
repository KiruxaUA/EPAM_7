package ua.epam6.IOCRUD.exceptions;

public class ChangesRejectedException extends Exception {
    public ChangesRejectedException(String fileName) {
        super("Changes rejected to " + fileName);
    }
}
