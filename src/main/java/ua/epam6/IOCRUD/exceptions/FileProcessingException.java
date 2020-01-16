package ua.epam6.IOCRUD.exceptions;

public class FileProcessingException extends Exception {
    public FileProcessingException(String fileName) {
        super("Error in file processing with " + fileName);
    }
}
