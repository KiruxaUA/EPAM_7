package ua.epam6.IOCRUD.service;

import ua.epam6.IOCRUD.model.Developer;
import ua.epam6.IOCRUD.repository.jdbc.JdbcDeveloperRepositoryImpl;

import java.util.List;

public class DeveloperService {
    private JdbcDeveloperRepositoryImpl jdbcDeveloperRepo = new JdbcDeveloperRepositoryImpl();

    public Developer create(Developer model) {
        return jdbcDeveloperRepo.create(model);
    }

    public Developer getById(Long ID) {
        return jdbcDeveloperRepo.getById(ID);
    }

    public Developer update(Developer model) {
        return jdbcDeveloperRepo.update(model);
    }

    public void delete(Long ID) {
        jdbcDeveloperRepo.delete(ID);
    }

    public List<Developer> getAll() {
        return jdbcDeveloperRepo.getAll();
    }
}