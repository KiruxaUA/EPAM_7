package ua.epam6.IOCRUD.exceptions;

public class NoSuchElementException extends Exception {
    public NoSuchElementException() {
        super("No such element(s) found!");
    }
}
