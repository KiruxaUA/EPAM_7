package ua.epam6.IOCRUD.view;

import ua.epam6.IOCRUD.controller.DeveloperController;
import ua.epam6.IOCRUD.utils.InputReader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class DeveloperView {
    private InputReader inputReader;
    private DeveloperController controller = new DeveloperController();

    DeveloperView(InputReader inputReader) {
        this.inputReader = inputReader;
    }

    private String menu = "1: Get developers\n" +
            "2: Get developers by id\n" +
            "3: Add new developer\n" +
            "4: Set developer's skills\n" +
            "5: Back to main menu";

    boolean run() throws Exception {
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
                    create();
                    break;
                }
                case 4: {
                    update();
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

    private void create() throws Exception {
        System.out.println("Enter developer's first name: ");
        String firstName = inputReader.getStringInput();
        System.out.println("Enter developer's last name: ");
        String lastName = inputReader.getStringInput();
        System.out.println("Enter data of developer`s account: ");
        String accountData = inputReader.getStringInput();
        System.out.println("Enter developer`s skill(s): ");
        long skillChoice;
        Set<Long> skillsId = new HashSet<>();
        while ((skillChoice = inputReader.getIntInput()) != 0) {
            skillsId.add(skillChoice);
        }
        System.out.println(controller.addNewDeveloper(firstName, lastName, accountData, skillsId));
    }

    private void update() throws Exception {
        System.out.println("Enter ID of developer: ");
        int devId = inputReader.getIntInput();
        System.out.println(controller.getAllAccounts());
        System.out.println("AccountMessage ID: ");
        int accId = inputReader.getIntInput();
        System.out.println(controller.setAccount(devId, accId));
        List<Long> skillIds = new ArrayList<>();
        System.out.println("Enter skills ID`s or 0 to end input");
        System.out.println(controller.getAllSkills());
        long id;
        do {
            id = inputReader.getIntInput();
            if (id > 0) {
                skillIds.add(id);
            }
        } while (id != 0);
        System.out.println(controller.setSkills(devId, skillIds));
    }
}