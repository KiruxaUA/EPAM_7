package ua.epam6.IOCRUD.repository.javaio;

import ua.epam6.IOCRUD.exceptions.*;
import ua.epam6.IOCRUD.model.Account;
import ua.epam6.IOCRUD.model.AccountStatus;
import ua.epam6.IOCRUD.repository.AccountRepository;
import ua.epam6.IOCRUD.utils.FileProcessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AccountRepositoryImpl implements AccountRepository {
    private FileProcessor fileProcessor = new FileProcessor("src\\main\\resources\\Account.txt");

    public Account readByID(Long id) {
        Account account = null;
        try {
            for(String line : fileProcessor.readFile()) {
                if(line.startsWith("id:" + id)) {
                    account = deserialize(line);
                    break;
                }
            }
        } catch (ChangesRejectedException e) {
            e.printStackTrace();
        }
        return account;
    }

    public List<Account> readAll() {
        List<Account> accounts = new ArrayList<Account>();
        try {
            for (String line : fileProcessor.readFile()) {
                Account account = deserialize(line);
                if (account.getId() != null && account.getAccountStatus() != null && account.getName() != null) {
                    accounts.add(account);
                }
            }
        } catch (ChangesRejectedException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public void create(Account account) {
        List<Account> accounts = readAll();
        accounts.add(account);
        serialize(accounts);
    }

    public void delete(Account account) {
        List<Account> accounts = readAll();
        if (accounts.remove(account)) {
            serialize(accounts);
        }
    }

    public void update(Account account) throws NoSuchElementException {
        List<Account> accounts = readAll();
        boolean updated = accounts.removeIf(acc -> acc.getId().equals(account.getId()));
        if (!updated) {
            throw new NoSuchElementException();
        }
        accounts.add(account);
        serialize(accounts);
    }

    private String stringify(Account account) {
        return "id:" + account.getId() + "/name:" + account.getName() + "/status:" + account.getAccountStatus();
    }

    private void serialize(Collection<Account> collection) {
        List<String> serialized = collection.stream().map(this::stringify).collect(Collectors.toList());
        try {
            fileProcessor.writeFile(serialized);
        } catch (ChangesRejectedException e) {
            e.printStackTrace();
        }
    }

    private Account deserialize(String line) {
        Long id = null;
        String name = null;
        AccountStatus status = null;
        String[] tokens = line.split("/");
        for (String token : tokens) {
            if (token.startsWith("id:")) {
                id = Long.parseLong(token.substring(3));
            }
            if (token.startsWith("name:")) {
                name = token.substring(5);
            }
            if (token.startsWith("status:")) {
                status = AccountStatus.valueOf(token.substring(7));
            }
        }

        return new Account(id, name, status);
    }
}
