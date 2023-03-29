package org.defendev.easygo.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@RequestMapping(path = {
    "/"
})
@Controller
public class EfaPageController {

    @RequestMapping(path = {""}, method = RequestMethod.GET)
    public ModelAndView efaPage() {
        final ModelAndView mav = new ModelAndView();
        mav.setViewName("/index.html");
        return mav;
    }

}
