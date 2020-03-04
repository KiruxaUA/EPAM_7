package ua.epam6.IOCRUD.service;

import org.apache.log4j.Logger;
import ua.epam6.IOCRUD.model.Developer;
import ua.epam6.IOCRUD.repository.DeveloperRepository;
import ua.epam6.IOCRUD.repository.jdbc.JdbcDeveloperRepositoryImpl;
import ua.epam6.IOCRUD.service.servicevisitors.ServiceVisitor;

import java.util.List;

public class DeveloperService implements Serviceable {
    private static final Logger log = Logger.getLogger(AccountService.class);
    private DeveloperRepository developerRepository;

    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public void create(Developer model) throws Exception {
        log.debug("Executing developer creation...");
        developerRepository.create(model);
    }

    public Developer getById(Long ID) throws Exception{
        log.debug("Executing developer getting by ID...");
        return developerRepository.getById(ID);
    }

    public void update(Developer model) throws Exception {
        log.debug("Executing developer updating...");
        developerRepository.update(model);
    }

    public void delete(Long ID) throws Exception {
        log.debug("Executing developer deletion...");
        developerRepository.delete(ID);
    }

    public List<Developer> getAll() throws Exception {
        log.debug("Executing developers information...");
        return developerRepository.getAll();
    }

    @Override
    public void doService(ServiceVisitor visitor) {
        visitor.visitDeveloperService(this);
    }
}