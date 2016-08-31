package ee.aktors.demo.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public class IndexController {

    @RequestMapping(value = "/")
    public ModelAndView index(ModelMap model) {
        return new ModelAndView("index", model);
    }

}
