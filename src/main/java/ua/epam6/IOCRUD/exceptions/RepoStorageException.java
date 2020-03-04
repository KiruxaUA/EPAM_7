package ua.epam6.IOCRUD.exceptions;

public class RepoStorageException extends RuntimeException {
    public RepoStorageException() {
        super();
    }
    public RepoStorageException(String message) {
        super(message);
    }
}
