package ua.epam6.IOCRUD.view;

import ua.epam6.IOCRUD.utils.InputReader;

import java.util.Scanner;

public class AppView {
    private static Scanner scanner = new Scanner(System.in);

    private InputReader inputReader = new InputReader();

    private String pattern = "[1234]";
    private String messageChoice = "Choose an option of menu: ";
    private String messageError = "Error occurred while processing option input! Try to fit the rules.";

    public void viewApp() {
        do {
            System.out.println("1.Developers\n2.Skills\n3.Accounts\n4.Exit");
            switch(validateInput(pattern, messageChoice, messageError)) {
                case 1:
                    DeveloperView developerView = new DeveloperView(inputReader);
                    while (!developerView.run());
                    break;
                case 2:
                    SkillView skillView = new SkillView(inputReader);
                    while (!skillView.run());
                    break;
                case 3:
                    AccountView accountView = new AccountView(inputReader);
                    while (!accountView.run());
                    break;
                case 4:
                    inputReader.close();
                    System.exit(0);
                }
        } while(true);
    }

    private static int validateInput(String pattern, String messageChoice, String messageError) {
        do {
            System.out.println(messageChoice);
            if(!scanner.hasNext(pattern)) {
                scanner.next();
                System.out.println(messageError);
                continue;
            }
            return scanner.nextInt();
        } while(true);
    }
}