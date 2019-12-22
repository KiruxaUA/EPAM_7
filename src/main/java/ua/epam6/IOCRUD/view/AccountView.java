package ua.epam6.IOCRUD.view;

import ua.epam6.IOCRUD.controller.AccountController;
import ua.epam6.IOCRUD.exceptions.ChangesRejectedException;
import ua.epam6.IOCRUD.exceptions.NoSuchElementException;
import ua.epam6.IOCRUD.utils.InputReader;

public class AccountView {
    private InputReader inputReader;
    private AccountController controller = new AccountController();

    private String menu = "1: Get data from all accounts\n" +
                    "2: Get data from a specific account\n" +
                    "3: Add new account\n" +
                    "4: Update account\n" +
                    "5: Back to main menu\n";

    public AccountView(InputReader reader) {
        this.inputReader = reader;
    }

    public boolean run() throws NoSuchElementException, ChangesRejectedException {
        while (true) {
            System.out.println(menu);
            int choice = inputReader.getIntInput();

            switch (choice) {
                case 1: {
                    System.out.println(controller.getAll());
                    break;
                }
                case 2: {
                    System.out.println("Enter ID of the account: ");
                    int input = inputReader.getIntInput();
                    System.out.println(controller.getById(input));
                    break;
                }
                case 3: {
                    System.out.println("Input account data: ");
                    String data = inputReader.getStringInput();
                    System.out.println(controller.create(data));
                    break;
                }
                case 4: {
                    System.out.println("Enter ID of the account: ");
                    int input = inputReader.getIntInput();
                    System.out.println("Input account of data: ");
                    String data = inputReader.getStringInput();
                    System.out.println(controller.update(input, data));
                    break;
                }
                case 5: {
                    return true;
                }
                default: {
                    System.out.println("Incorrect choice");
                }
                return false;
            }
        }
    }

}
