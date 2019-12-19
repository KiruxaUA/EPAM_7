package ua.epam6.IOCRUD.controller;

import ua.epam6.IOCRUD.exceptions.ChangesRejectedException;
import ua.epam6.IOCRUD.exceptions.NoSuchElementException;
import ua.epam6.IOCRUD.model.Account;
import ua.epam6.IOCRUD.model.AccountStatus;
import ua.epam6.IOCRUD.repository.AccountRepository;
import ua.epam6.IOCRUD.repository.javaio.AccountRepositoryImpl;

import java.util.List;

public class AccountController {
    private AccountRepository repo = new AccountRepositoryImpl();

    public String getAll() throws NoSuchElementException, ChangesRejectedException {
        StringBuilder stringBuilder = new StringBuilder();
        List<Account> accounts = repo.readAll();

        for (Account account : accounts) {
            stringBuilder.append(account);
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }


    public String getById(long id) throws NoSuchElementException {
        Account account = repo.readByID(id);
        if (account == null) {
            return "Account not found";
        }
        else {
            return account.toString();
        }
    }

    public String create(String data) throws ChangesRejectedException, NoSuchElementException {
        long id = repo.getLastId() + 1;
        Account account = new Account(id, data, AccountStatus.ACTIVE);
        List<Account> accounts = repo.readAll();
        if (accounts.contains(account)) {
            return "Account already exist!";
        } else {
            repo.create(account);
            return "Account created";
        }
    }

    public String update(long input, String data) throws ChangesRejectedException {
        Account account = new Account(input, data, AccountStatus.ACTIVE);
        try {
            repo.update(account);
            return "Operation completed successfully";
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return "Operation failed";
        }
    }
}
