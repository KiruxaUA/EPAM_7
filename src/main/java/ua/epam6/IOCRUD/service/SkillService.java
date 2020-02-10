package ua.epam6.IOCRUD.service;

import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.repository.SkillRepository;
import ua.epam6.IOCRUD.repository.jdbc.JdbcSkillRepositoryImpl;

import java.util.List;

public class SkillService {
    private SkillRepository jdbcSkillRepo = new JdbcSkillRepositoryImpl();

    public Skill create(Skill model) throws Exception {
        return jdbcSkillRepo.create(model);
    }

    public Skill getById(Long ID) throws Exception {
        return jdbcSkillRepo.getById(ID);
    }

    public Skill update(Skill model) throws Exception {
        return jdbcSkillRepo.update(model);
    }

    public void delete(Long ID) throws Exception {
        jdbcSkillRepo.delete(ID);
    }

    public List<Skill> getAll() throws Exception {
        return jdbcSkillRepo.getAll();
    }
}