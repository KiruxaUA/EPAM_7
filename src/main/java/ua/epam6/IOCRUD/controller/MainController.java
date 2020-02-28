package ua.epam6.IOCRUD.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    @GetMapping(value = "/")
    public ModelAndView viewIndexPage(){
        return new ModelAndView("index");
    }
    @GetMapping(value = "/documentation")
    public ModelAndView viewDocPage(){
        return new ModelAndView("doc");
    }

}
