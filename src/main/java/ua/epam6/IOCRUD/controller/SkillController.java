package ua.epam6.IOCRUD.controller;

import ua.epam6.IOCRUD.exceptions.NoSuchElementException;
import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.repository.javaio.SkillRepositoryImpl;

import java.util.List;

public class SkillController {
    private SkillRepositoryImpl repo = new SkillRepositoryImpl();

    public String getAll() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Skill> skills = repo.getAll();

        for (Skill skill : skills) {
            stringBuilder.append(skill);
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public String getById(long id) {
        Skill skill = repo.getById(id);
        if (skill == null) {
            return "Skill not found";
        } else {
            return skill.toString();
        }
    }

    public String addNewSkill(String name) {
        Long id = repo.getLastId() + 1;
        List<Skill> skills = repo.getAll();
        Skill skill = new Skill(id, name);
        if (skills.contains(skill)) {
            return "Skill already exists";
        } else {
            repo.create(skill);
            return "Skill added to repository";
        }
    }

    public String update(long id, String name) {
        Skill skill = new Skill(id, name);
        try {
            repo.update(skill);
            return "Operation completed successfully";
        } catch (NoSuchElementException e) {
            e.getMessage();
            return "Operation failed";
        }
    }

    public String delete(long input) {
        Skill skill = repo.getById(input);
        List<Skill> skills = repo.getAll();
        if (skills.remove(skill)) {
            return "Operation completed successfully";
        }
        else {
            return "Operation failed";
        }
    }
}
