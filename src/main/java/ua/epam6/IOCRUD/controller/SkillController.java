package ua.epam6.IOCRUD.controller;

import org.apache.log4j.Logger;
import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.service.SkillService;

import java.util.List;

public class SkillController {
    private static final Logger log = Logger.getLogger(AccountController.class);
    private SkillService service = new SkillService();

    public String getAll() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        List<Skill> skills = service.getAll();

        for (Skill skill : skills) {
            stringBuilder.append(skill);
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public String getById(long id) throws Exception {
        Skill skill = service.getById(id);
        if (skill == null) {
            return "Skill not found";
        } else {
            return skill.toString();
        }
    }

    public String addNewSkill(String name) throws Exception{
        List<Skill> skills = service.getAll();
        Skill skill = new Skill(null, name);
        if (skills.contains(skill)) {
            return "Skill already exists";
        } else {
            service.create(skill);
            return "Skill added to repository";
        }
    }

    public String update(long id, String name) {
        Skill skill = new Skill(id, name);
        try {
            service.update(skill);
            return "Operation completed successfully";
        } catch (Exception e) {
            log.error("Error occurred while updating record(MySQL)");
            return "Operation failed";
        }
    }

    public String delete(long ID) {
        try {
            service.delete(ID);
            return "Operation completed successfully";
        } catch (Exception e) {
            log.error("Error occurred while deleting record(MySQL)");
            return "Operation failed";
        }
    }
}