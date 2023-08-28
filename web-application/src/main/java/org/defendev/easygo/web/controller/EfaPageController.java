package org.defendev.easygo.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@RequestMapping(path = {
    "/",
    "/srcdoc",
    "/srcdoc/mgmt/browse",
    "/srcdoc/mgmt/details/{externalId}",
    "/srcdoc/stats"
})
@Controller
public class EfaPageController {

    @RequestMapping(path = {""}, method = RequestMethod.GET)
    public ModelAndView efaPage() {
        final ModelAndView mav = new ModelAndView();
        /* todo: the slash at the beginning (/efa-app.index.html) is needed because the InternalResourceViewResolver
         *   is used (see WebContext). Probably deserves a rewrite to Thymeleaf anyway.
         */
        mav.setViewName("efa-app/efa-app.index.th");
        return mav;
    }

}
