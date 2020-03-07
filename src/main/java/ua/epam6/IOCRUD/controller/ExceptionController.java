package ua.epam6.IOCRUD.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import ua.epam6.IOCRUD.exceptions.NoSuchEntryException;
import ua.epam6.IOCRUD.exceptions.RepoStorageException;

@RestControllerAdvice
public class ExceptionController {
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchEntryException.class)
    public ModelAndView handlingNoSuchEntryException() {
        return new ModelAndView("error404");
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RepoStorageException.class)
    public ModelAndView handlingRepoStorageException() {
        return new ModelAndView("error500");
    }
}