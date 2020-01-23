package ua.epam6.IOCRUD.service;

import ua.epam6.IOCRUD.model.Account;
import ua.epam6.IOCRUD.repository.jdbc.JdbcAccountRepositoryImpl;

import java.util.List;

public class AccountService {
    private JdbcAccountRepositoryImpl jdbcAccountRepo = new JdbcAccountRepositoryImpl();

    public Account create(Account model) {
        return jdbcAccountRepo.create(model);
    }

    public Account getById(Long ID) {
        return jdbcAccountRepo.getById(ID);
    }

    public Account update(Account model) {
        return jdbcAccountRepo.update(model);
    }

    public void delete(Long ID) {
        jdbcAccountRepo.delete(ID);
    }

    public List<Account> getAll() {
        return jdbcAccountRepo.getAll();
    }
}