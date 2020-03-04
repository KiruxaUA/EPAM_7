package ua.epam6.IOCRUD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.epam6.IOCRUD.model.Developer;
import ua.epam6.IOCRUD.service.Serviceable;
import ua.epam6.IOCRUD.service.servicevisitors.ServiceVisitor;
import ua.epam6.IOCRUD.service.servicevisitors.VisitorFactory;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1")
public class DeveloperController {
    private final Serviceable service;

    @Autowired
    public DeveloperController(@Qualifier("developerService") Serviceable service) {
        this.service = service;
    }

    @GetMapping(value = "developers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Developer> getById(@RequestParam(value = "id") Long id) {
        ServiceVisitor visitor = VisitorFactory.getVisitorByOperation(VisitorFactory.GET_BY_ID, id);
        service.doService(visitor);
        if(visitor.getResultData() != null && visitor.getResultData() instanceof Developer) {
            return new ResponseEntity<>((Developer) visitor.getResultData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "developers")
    public ResponseEntity<List<?>> getAll(){
        ServiceVisitor visitor = VisitorFactory.getVisitorByOperation(VisitorFactory.GET_ALL, null);
        service.doService(visitor);
        if(visitor.getResultData() != null && visitor.getResultData() instanceof List<?>) {
            return new ResponseEntity<>((List<?>) visitor.getResultData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "developers")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void create(@RequestBody Developer developer) {
        service.doService(VisitorFactory.getVisitorByOperation(VisitorFactory.CREATE, developer));
    }

    @PutMapping(value = "developers")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Developer developer) {
        service.doService(VisitorFactory.getVisitorByOperation(VisitorFactory.UPDATE, developer));
    }

    @DeleteMapping(value = "developers/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@RequestParam(value = "id") Long id){
        service.doService(VisitorFactory.getVisitorByOperation(VisitorFactory.DELETE, id));
    }
}