package org.defendev.easygo.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.easygo.web.config.WebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import static org.apache.commons.lang3.StringUtils.removeStart;
import static org.defendev.easygo.web.config.WebSecurity.OAUTH2_REGISTRATION_ID_AZURE;
import static org.defendev.easygo.web.config.WebSecurity.OAUTH2_REGISTRATION_ID_GOOGLE;
import static org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;



@RequestMapping( path = { "/" } )
@Controller
public class FormLoginPageController {

    private static final Logger log = LogManager.getLogger();

    @RequestMapping(path = WebSecurity.SIGN_IN_PATH, method = RequestMethod.GET)
    public ModelAndView loginPage() {
        final ModelAndView mav = new ModelAndView();
        final String azureSignInLink = removeStart(DEFAULT_AUTHORIZATION_REQUEST_BASE_URI, "/") + "/" +
            OAUTH2_REGISTRATION_ID_AZURE;
        mav.getModel().put("azureSignInLink", azureSignInLink);
        final String googleSignInLink = removeStart(DEFAULT_AUTHORIZATION_REQUEST_BASE_URI, "/") + "/" +
            OAUTH2_REGISTRATION_ID_GOOGLE;
        mav.getModel().put("googleSignInLink", googleSignInLink);
        mav.setViewName("static/loginPage.th.html");
        return mav;
    }

}
