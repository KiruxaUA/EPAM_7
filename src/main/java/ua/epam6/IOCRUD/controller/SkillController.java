package ua.epam6.IOCRUD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.epam6.IOCRUD.model.Skill;
import ua.epam6.IOCRUD.service.Serviceable;
import ua.epam6.IOCRUD.service.servicevisitors.ServiceVisitor;
import ua.epam6.IOCRUD.service.servicevisitors.VisitorFactory;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1")
public class SkillController {
    private final Serviceable service;

    @Autowired
    public SkillController(@Qualifier("skillService") Serviceable service) {
        this.service = service;
    }

    @GetMapping(value = "/skill", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Skill> getById(@RequestParam(value = "id") Long id){
        ServiceVisitor visitor = VisitorFactory.getVisitorByOperation(VisitorFactory.GET_BY_ID, id);
        service.doService(visitor);
        if(visitor.getResultData() != null && visitor.getResultData() instanceof Skill) {
            return new ResponseEntity<>((Skill) visitor.getResultData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/skills")
    public ResponseEntity<List<?>> getAll(){
        ServiceVisitor visitor = VisitorFactory.getVisitorByOperation(VisitorFactory.GET_ALL, null);
        service.doService(visitor);
        if(visitor.getResultData() != null && visitor.getResultData() instanceof List<?>) {
            return new ResponseEntity<>((List<?>) visitor.getResultData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/skills")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void create(@RequestBody Skill skill) {
        service.doService(VisitorFactory.getVisitorByOperation(VisitorFactory.CREATE, skill));
    }

    @PutMapping(value = "/skills")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Skill skill) {
        service.doService(VisitorFactory.getVisitorByOperation(VisitorFactory.UPDATE, skill));
    }

    @DeleteMapping(value = "/skills")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@RequestParam(value = "id") Long id){
        service.doService(VisitorFactory.getVisitorByOperation(VisitorFactory.DELETE, id));
    }
}