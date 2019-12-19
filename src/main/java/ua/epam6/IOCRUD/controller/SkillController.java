package ua.epam6.IOCRUD.controller;

import ua.epam6.IOCRUD.exceptions.ChangesRejectedException;
import ua.epam6.IOCRUD.exceptions.NoSuchElementException;
import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.repository.SkillRepository;
import ua.epam6.IOCRUD.repository.javaio.SkillRepositoryImpl;

import java.util.List;

public class SkillController {
    private SkillRepository repo = new SkillRepositoryImpl();

    public String getAll() throws NoSuchElementException, ChangesRejectedException {
        StringBuilder stringBuilder = new StringBuilder();
        List<Skill> skills = repo.readAll();

        for (Skill skill : skills) {
            stringBuilder.append(skill);
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public String getById(long id) throws NoSuchElementException {
        Skill skill = repo.readByID(id);
        if (skill == null) {
            return "Skill not found";
        } else {
            return skill.toString();
        }
    }

    public String addNewSkill(String name) throws NoSuchElementException, ChangesRejectedException {
        Long id = repo.getLastId() + 1;
        Skill skill = new Skill(id, name);
        List<Skill> skills = repo.readAll();
        if (skills.contains(skill)) {
            return "Skill already exists";
        } else {
            repo.create(skill);
            return "Skill added to repository";
        }
    }

    public String update(long id, String name) throws ChangesRejectedException {
        Skill skill = new Skill(id, name);
        try {
            repo.update(skill);
            return "Operation completed successfully";
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return "Operation failed";
        }
    }

}
