package ua.epam6.IOCRUD.utils;

import ua.epam6.IOCRUD.exceptions.ChangesRejectedException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FileProcessor {
    private String fileName;

    public FileProcessor(String fileName) {
        this.fileName = fileName;
    }

    public List<String> readFile() throws ChangesRejectedException {
        List<String> lines = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String stringLine;
            while ((stringLine = reader.readLine()) != null) {
                lines.add(stringLine);
            }
        } catch (IOException e) {
            throw new ChangesRejectedException(fileName);
        }
        return lines;
    }

    public void writeFile(Collection<String> collection) throws ChangesRejectedException{
        try {
            PrintWriter writer = new PrintWriter(fileName);
            for (String s: collection) {
                writer.println(s);
            }
        } catch (IOException e) {
            throw new ChangesRejectedException(fileName);
        }
    }

}
