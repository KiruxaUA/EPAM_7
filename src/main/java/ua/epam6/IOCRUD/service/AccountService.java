package ua.epam6.IOCRUD.service;

import ua.epam6.IOCRUD.model.Account;
import ua.epam6.IOCRUD.repository.AccountRepository;
import ua.epam6.IOCRUD.repository.jdbc.JdbcAccountRepositoryImpl;

import java.util.List;

public class AccountService {
    private AccountRepository jdbcAccountRepo = new JdbcAccountRepositoryImpl();

    public Account create(Account model) throws Exception {
        return jdbcAccountRepo.create(model);
    }

    public Account getById(Long ID) throws Exception {
        return jdbcAccountRepo.getById(ID);
    }

    public Account update(Account model) throws Exception {
        return jdbcAccountRepo.update(model);
    }

    public void delete(Long ID) throws Exception {
        jdbcAccountRepo.delete(ID);
    }

    public List<Account> getAll() throws Exception {
        return jdbcAccountRepo.getAll();
    }
}