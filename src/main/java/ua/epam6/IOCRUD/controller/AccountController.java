package ua.epam6.IOCRUD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.epam6.IOCRUD.model.Account;
import ua.epam6.IOCRUD.service.Serviceable;
import ua.epam6.IOCRUD.service.servicevisitors.ServiceVisitor;
import ua.epam6.IOCRUD.service.servicevisitors.VisitorFactory;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1")
public class AccountController {
    private final Serviceable service;

    @Autowired
    public AccountController(@Qualifier("accountService") Serviceable service) {
        this.service = service;
    }

    @GetMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getById(@RequestParam(value = "id") Long id) {
        ServiceVisitor visitor = VisitorFactory.getVisitorByOperation(VisitorFactory.GET_BY_ID, id);
        service.doService(visitor);
        if(visitor.getResultData() != null && visitor.getResultData() instanceof Account){
            return new ResponseEntity<>((Account) visitor.getResultData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/accounts")
    public ResponseEntity<List<?>> getAll() {
        ServiceVisitor visitor = VisitorFactory.getVisitorByOperation(VisitorFactory.GET_ALL, null);
        service.doService(visitor);
        if(visitor.getResultData() != null && visitor.getResultData() instanceof List<?>){
            return new ResponseEntity<>((List<?>) visitor.getResultData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/accounts")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void create(@RequestBody Account account){
        service.doService(VisitorFactory.getVisitorByOperation(VisitorFactory.CREATE, account));
    }

    @PutMapping(value = "/accounts")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Account account){
        service.doService(VisitorFactory.getVisitorByOperation(VisitorFactory.UPDATE, account));
    }

    @DeleteMapping(value = "/accounts")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@RequestParam(value = "id") Long id){
        service.doService(VisitorFactory.getVisitorByOperation(VisitorFactory.DELETE, id));
    }
}