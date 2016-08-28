package ee.aktors.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public class WebController {

    @RequestMapping(value = "/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

}
