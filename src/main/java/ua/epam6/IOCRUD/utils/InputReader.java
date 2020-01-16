package ua.epam6.IOCRUD.utils;

import java.util.Scanner;

public class InputReader {
    private Scanner scanner = new Scanner(System.in);

    public int getIntInput() {
        int result;
        while(true) {
            System.out.print("Your choice: ");
            if(scanner.hasNextInt()) {
                result = scanner.nextInt();
                return result;
            }
            else {
                System.out.println("Wrong input");
                scanner.next();
            }
        }
    }

    public String getStringInput() {
        scanner.nextLine();
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}
