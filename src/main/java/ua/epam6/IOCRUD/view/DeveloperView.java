package ua.epam6.IOCRUD.view;

import ua.epam6.IOCRUD.controller.DeveloperController;
import ua.epam6.IOCRUD.exceptions.ChangesRejectedException;
import ua.epam6.IOCRUD.exceptions.NoSuchElementException;
import ua.epam6.IOCRUD.repository.SkillRepository;
import ua.epam6.IOCRUD.repository.javaio.AccountRepositoryImpl;
import ua.epam6.IOCRUD.utils.InputReader;

import java.util.HashSet;
import java.util.Set;

public class DeveloperView {
    private InputReader inputReader;
    private DeveloperController controller;
    private String menu = "1: Get data of dev's\n" +
            "2: Get dev's data by id\n" +
            "3: Add new developer\n" +
            "4: Update dev's data\n" +
            "5: Back to main menu\n";

    public DeveloperView(InputReader reader, SkillRepository skillRepository, AccountRepositoryImpl accountRepository) {
        this.inputReader = reader;
        this.controller = new DeveloperController(skillRepository, accountRepository);
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

    private void create() throws NoSuchElementException, ChangesRejectedException {
        System.out.println("Enter developer's name: ");
        String name = inputReader.getStringInput();
        System.out.println("Enter data of developer`s account");
        String accountData = inputReader.getStringInput();
        long skillChoice;
        Set<Long> skillsId = new HashSet<Long>();
        while ((skillChoice = inputReader.getIntInput()) != 0) {
            skillsId.add(skillChoice);
        }
        System.out.println(controller.addNewDeveloper(name, accountData, skillsId));
    }

    private void update() {
        System.out.println("Enter developer's name: ");
        int devId = inputReader.getIntInput();
        System.out.println("Choose id of dev`s skills: ");
        Set<Long> skillsId = new HashSet<Long>();
        long skillChoice;
        while ((skillChoice = inputReader.getIntInput()) != 0) {
            skillsId.add(skillChoice);
        }
    }
}
