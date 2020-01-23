package ua.epam6.IOCRUD.view;

import ua.epam6.IOCRUD.controller.AccountController;
import ua.epam6.IOCRUD.utils.InputReader;

class AccountView {
    private InputReader inputReader;
    private AccountController controller = new AccountController();

    private String menu = "1: Get data from all accounts\n" +
                    "2: Get data from a specific account\n" +
                    "3: Add new account\n" +
                    "4: Update account\n" +
                    "5: Delete account\n" +
                    "6: Back to main menu";

    AccountView(InputReader reader) {
        this.inputReader = reader;
    }

    boolean run() {
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
                    int ID = inputReader.getIntInput();
                    System.out.println(controller.getById(ID));
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
                    int ID = inputReader.getIntInput();
                    System.out.println("Input account of data: ");
                    String data = inputReader.getStringInput();
                    System.out.println(controller.update(ID, data));
                    break;
                }
                case 5: {
                    System.out.println("Enter ID of the account: ");
                    int ID = inputReader.getIntInput();
                    System.out.println(controller.delete(ID));
                }
                case 6: {
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
