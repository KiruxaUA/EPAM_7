package ua.epam6.IOCRUD.service;

import org.apache.log4j.Logger;
import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.repository.SkillRepository;
import ua.epam6.IOCRUD.repository.jdbc.JdbcSkillRepositoryImpl;
import ua.epam6.IOCRUD.service.servicevisitors.ServiceVisitor;

import java.util.List;

public class SkillService implements Serviceable {
    private static final Logger log = Logger.getLogger(SkillService.class);
    private SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public void create(Skill model) throws Exception {
        log.debug("Executing skill creation...");
        skillRepository.create(model);
    }

    public Skill getById(Long ID) throws Exception {
        log.debug("Executing skill getting by ID...");
        return skillRepository.getById(ID);
    }

    public void update(Skill model) throws Exception {
        log.debug("Executing skill updating...");
        skillRepository.update(model);
    }

    public void delete(Long ID) throws Exception {
        log.debug("Executing skill deletion...");
        skillRepository.delete(ID);
    }

    public List<Skill> getAll() throws Exception {
        log.debug("Executing skills information...");
        return skillRepository.getAll();
    }

    @Override
    public void doService(ServiceVisitor visitor) {
        visitor.visitSkillService(this);
    }
}