package ua.epam6.IOCRUD;

import ua.epam6.IOCRUD.exceptions.ChangesRejectedException;
import ua.epam6.IOCRUD.exceptions.NoSuchElementException;
import ua.epam6.IOCRUD.utils.InputReader;
import ua.epam6.IOCRUD.view.AccountView;

public class AppRunner {
    public static void main(String[] args) throws NoSuchElementException, ChangesRejectedException {
        AccountView view = new AccountView(new InputReader());
        view.run();
    }
}
