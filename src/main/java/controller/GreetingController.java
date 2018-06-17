package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GreetingController {

    /**
     * wyswietla strone powitalna
     * przyklad:
     * http://localhost:8080/
     * @return
     */
    @GetMapping("/")
    public ModelAndView index()
    {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.setViewName("/index");
        return modelAndView;
    }
}
