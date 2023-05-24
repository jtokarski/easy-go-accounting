package org.defendev.easygo.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.easygo.web.config.WebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@RequestMapping( path = { "/" } )
@Controller
public class FormLoginPageController {

    private static final Logger log = LogManager.getLogger();

    @RequestMapping(path = WebSecurity.SIGN_IN_PATH, method = RequestMethod.GET)
    public ModelAndView loginPage() {
        final ModelAndView mav = new ModelAndView();
        mav.setViewName("/loginPage.html");
        return mav;
    }

}


