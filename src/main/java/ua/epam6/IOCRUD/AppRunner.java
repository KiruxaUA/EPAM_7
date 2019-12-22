package ua.epam6.IOCRUD;

import ua.epam6.IOCRUD.exceptions.ChangesRejectedException;
import ua.epam6.IOCRUD.exceptions.NoSuchElementException;
import ua.epam6.IOCRUD.view.AppView;

public class AppRunner {
    public static void main(String[] args) throws NoSuchElementException, ChangesRejectedException {
        AppView view = new AppView();
        view.viewApp();
    }
}
