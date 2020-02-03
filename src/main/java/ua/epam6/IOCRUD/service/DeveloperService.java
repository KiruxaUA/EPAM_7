package ua.epam6.IOCRUD.service;

import ua.epam6.IOCRUD.model.Developer;
import ua.epam6.IOCRUD.repository.DeveloperRepository;
import ua.epam6.IOCRUD.repository.jdbc.JdbcDeveloperRepositoryImpl;

import java.util.List;

public class DeveloperService {
    private DeveloperRepository jdbcDeveloperRepo = new JdbcDeveloperRepositoryImpl();

    public Developer create(Developer model) throws Exception {
        return jdbcDeveloperRepo.create(model);
    }

    public Developer getById(Long ID) throws Exception{
        return jdbcDeveloperRepo.getById(ID);
    }

    public Developer update(Developer model) throws Exception {
        return jdbcDeveloperRepo.update(model);
    }

    public void delete(Long ID) throws Exception {
        jdbcDeveloperRepo.delete(ID);
    }

    public List<Developer> getAll() throws Exception {
        return jdbcDeveloperRepo.getAll();
    }
}