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
    private FileProcessor fileProcessor = new FileProcessor("src\\main\\resources\\files\\accounts.txt");

    public Account getById(Long id) {
        Account account = null;
        try {
            for(String line: fileProcessor.readFile()) {
                if(line.startsWith("id:" + id)) {
                    account = deserialize(line);
                    break;
                }
            }
        } catch (FileProcessingException e) {
            e.getMessage();
        }
        return account;
    }

    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();
        try {
            for (String line : fileProcessor.readFile()) {
                Account account = deserialize(line);
                if (account.getId() != null && account.getAccountStatus() != null && account.getName() != null) {
                    accounts.add(account);
                }
            }
        } catch (FileProcessingException e) {
            e.getMessage();
        }
        return accounts;
    }

    public Long getLastId() {
        List<String> entities;
        try {
            entities = fileProcessor.readFile();
            if (entities.size() == 0) {
                return 0L;
            }
            long[] IDs = new long[entities.size()];
            for (int i = 0; i < entities.size(); i++) {
                IDs[i] = deserialize(entities.get(i)).getId();
            }
            long max = IDs[0];
            for (long item : IDs) {
                if (item > max) {
                    max = item;
                }
            }
            return max;
        }
        catch (FileProcessingException e) {
            e.getMessage();
        }
        return null;
    }

    public Account create(Account account)  {
        List<Account> accounts = getAll();
        accounts.add(account);
        serialize(accounts);
        return account;
    }

    public void delete(Long ID) {
        List<Account> accounts = getAll();
        Account searchedAccount = accounts.stream().filter(e -> e.getId().equals(ID)).findAny().get();
        if (accounts.remove(searchedAccount)) {
            serialize(accounts);
        }
    }

    public Account update(Account account) throws NoSuchElementException {
        List<Account> accounts = getAll();
        boolean updated = accounts.removeIf(acc -> acc.getId().equals(account.getId()));
        if (!updated) {
            throw new NoSuchElementException();
        }
        accounts.add(account);
        serialize(accounts);
        return account;
    }

    private String stringify(Account account) {
        return "id:" + account.getId() + "/name:" + account.getName() + "/status:" + account.getAccountStatus();
    }

    private void serialize(Collection<Account> collection) {
        List<String> serialized = collection.stream().map(this::stringify).collect(Collectors.toList());
        try {
            fileProcessor.writeFile(serialized);
        } catch (FileProcessingException e) {
            e.getMessage();
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
