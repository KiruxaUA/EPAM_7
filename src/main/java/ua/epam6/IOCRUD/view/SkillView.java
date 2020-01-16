package ua.epam6.IOCRUD.view;

import ua.epam6.IOCRUD.controller.SkillController;
import ua.epam6.IOCRUD.utils.InputReader;

class SkillView {
    private InputReader inputReader;
    private SkillController controller = new SkillController();

    private String menu = "1: Get all skills\n" +
            "2: Get skill by ID\n" +
            "3: Add new skill\n" +
            "4: Update skill\n" +
            "5: Back to main menu\n";

    SkillView(InputReader reader) {
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
                    System.out.println("Enter ID of the skill: ");
                    int input = inputReader.getIntInput();
                    System.out.println(controller.getById(input));
                    break;
                }
                case 3: {
                    System.out.println("Enter the name of the skill");
                    String name = inputReader.getStringInput();
                    System.out.println(controller.addNewSkill(name));
                    break;
                }
                case 4: {
                    System.out.println("Enter ID of the skill: ");
                    int input = inputReader.getIntInput();
                    System.out.println("Enter the name of the skill");
                    String name = inputReader.getStringInput();
                    System.out.println(controller.update(input, name));
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
