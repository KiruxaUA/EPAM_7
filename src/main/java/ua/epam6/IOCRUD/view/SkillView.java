package ua.epam6.IOCRUD.view;

import ua.epam6.IOCRUD.controller.SkillController;
import ua.epam6.IOCRUD.exceptions.ChangesRejectedException;
import ua.epam6.IOCRUD.exceptions.NoSuchElementException;
import ua.epam6.IOCRUD.utils.InputReader;

public class SkillView {
    private InputReader inputReader;
    private SkillController controller = new SkillController();

    private String menu = "1: print all skills\n" +
            "2: print skill by id\n" +
            "3: add new skill\n" +
            "4: update skill\n" +
            "5: exit menu\n";

    private String skillNameMessage = "Enter the name of the skill";
    private String idRequest = "enter id of the skill: ";
    private String unCorrect = "Uncorrect choice";

    public SkillView(InputReader reader) {
        this.inputReader = reader;
    }

    public void run() throws NoSuchElementException, ChangesRejectedException {
        outer:while (true) {
            System.out.println(menu);
            int choice = inputReader.getIntInput();

            switch (choice) {
                case 1: {
                    System.out.println(controller.getAll());
                    break;
                }
                case 2: {
                    System.out.print(idRequest);
                    int input = inputReader.getIntInput();
                    System.out.println(controller.getById(input));
                    break;
                }
                case 3: {
                    System.out.println(skillNameMessage);
                    String name = inputReader.getStringInput();
                    System.out.println(controller.addNewSkill(name));
                    break;
                }
                case 4: {
                    System.out.println(idRequest);
                    int input = inputReader.getIntInput();
                    System.out.println(skillNameMessage);
                    String name = inputReader.getStringInput();
                    System.out.println(controller.update(input, name));
                    break;
                }
                case 5: {
                    break outer;
                }
                default: {
                    System.out.println(unCorrect);
                }
            }
        }
    }
}
