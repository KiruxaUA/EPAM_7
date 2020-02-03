package ua.epam6.IOCRUD.controller;

import org.apache.log4j.Logger;
import ua.epam6.IOCRUD.model.Account;
import ua.epam6.IOCRUD.model.AccountStatus;
import ua.epam6.IOCRUD.service.AccountService;

import java.util.List;

public class AccountController {
    private static final Logger log = Logger.getLogger(AccountController.class);
    private AccountService service = new AccountService();

    public String getAll() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        List<Account> accounts = service.getAll();
        if (accounts == null) {
            return "Accounts not found";
        }
        else {
            for (Account account : accounts) {
                stringBuilder.append(account);
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }


    public String getById(long id) throws Exception {
        Account account = service.getById(id);
        if (account == null) {
            return "Account not found";
        }
        else {
            return account.toString();
        }
    }

    public String create(String data) throws Exception {
        Account account = new Account(null, data, AccountStatus.ACTIVE);
        List<Account> accounts = service.getAll();
        if (accounts.contains(account)) {
            return "Account already exist!";
        } else {
            service.create(account);
            return "Account created";
        }
    }


    public String update(long input, String data) {
        Account account = new Account(input, data, AccountStatus.ACTIVE);
        try {
            service.update(account);
            return "Operation completed successfully";
        } catch (Exception e) {
            log.error("Error occurred while updating record(MySQL)");
            return "Operation failed";
        }
    }

    public String delete(long ID) {
        try {
            service.delete(ID);
            return "Operation completed successfully";
        } catch (Exception e) {
            log.error("Error occurred while deleting record(MySQL)");
            return "Operation failed";
        }
    }
}