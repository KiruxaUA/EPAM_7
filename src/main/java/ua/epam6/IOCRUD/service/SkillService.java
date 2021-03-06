package ua.epam6.IOCRUD.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.repository.SkillRepository;
import ua.epam6.IOCRUD.repository.jdbc.JdbcSkillRepositoryImpl;
import ua.epam6.IOCRUD.service.servicevisitors.ServiceVisitor;

import java.util.List;

@Service("skillService")
public class SkillService implements Serviceable {
    private static final Logger log = Logger.getLogger(SkillService.class);
    private SkillRepository skillRepository;

    @Autowired
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