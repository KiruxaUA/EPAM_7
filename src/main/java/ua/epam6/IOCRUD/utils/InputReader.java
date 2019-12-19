package ua.epam6.IOCRUD.utils;

import java.util.Scanner;

public class InputReader {
    private Scanner scanner = new Scanner(System.in);

    public int getIntInput() {
        int result;
        do {
            System.out.print("Your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("\nWrong input");
                scanner.next();
            }
            result = scanner.nextInt();
        } while (result < 1);
        return result;
    }

    public String getStringInput() {
        scanner.nextLine();
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}
