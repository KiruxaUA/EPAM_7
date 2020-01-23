package ua.epam6.IOCRUD.utils;

import ua.epam6.IOCRUD.exceptions.FileProcessingException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FileProcessor {
    private String fileName;
    private String ioExceptionMessage = "Occurred problem with input/output to ";

    public FileProcessor(String fileName) {
        this.fileName = fileName;
    }

    public List<String> readFile() throws FileProcessingException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String stringLine;
            while ((stringLine = reader.readLine()) != null) {
                lines.add(stringLine);
            }
        }
        catch (IOException e) {
            System.out.println(ioExceptionMessage + fileName);
        }
        return lines;
    }

    public void writeFile(Collection<String> collection) throws FileProcessingException {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (String s: collection) {
                writer.println(s);
            }
        } catch (IOException e) {
            System.out.println(ioExceptionMessage + fileName);
        }
    }
}