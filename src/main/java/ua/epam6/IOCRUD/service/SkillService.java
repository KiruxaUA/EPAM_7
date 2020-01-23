package ua.epam6.IOCRUD.service;

import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.repository.jdbc.JdbcSkillRepositoryImpl;

import java.util.List;

public class SkillService {
    private JdbcSkillRepositoryImpl jdbcSkillRepo = new JdbcSkillRepositoryImpl();

    public Skill create(Skill model) {
        return jdbcSkillRepo.create(model);
    }

    public Skill getById(Long ID) {
        return jdbcSkillRepo.getById(ID);
    }

    public Skill update(Skill model) {
        return jdbcSkillRepo.update(model);
    }

    public void delete(Long ID) {
        jdbcSkillRepo.delete(ID);
    }

    public List<Skill> getAll() {
        return jdbcSkillRepo.getAll();
    }
}
