package org.defendev.easygo.web.controller;

import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.easygo.web.config.WebSecurity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.removeStart;
import static org.defendev.easygo.web.config.WebSecurity.OAUTH2_REGISTRATION_ID_AZURE;
import static org.defendev.easygo.web.config.WebSecurity.OAUTH2_REGISTRATION_ID_GOOGLE;
import static org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;
import static org.springframework.security.web.WebAttributes.AUTHENTICATION_EXCEPTION;



@RequestMapping( path = { "/" } )
@Controller
public class FormLoginPageController {

    private static final Logger log = LogManager.getLogger();

    @RequestMapping(path = WebSecurity.SIGN_IN_PATH, method = RequestMethod.GET)
    public ModelAndView loginPage(HttpSession session) {
        final ModelAndView mav = new ModelAndView();

        final String azureSignInLink = removeStart(DEFAULT_AUTHORIZATION_REQUEST_BASE_URI, "/") + "/" +
            OAUTH2_REGISTRATION_ID_AZURE;
        mav.getModel().put("azureSignInLink", azureSignInLink);

        final String googleSignInLink = removeStart(DEFAULT_AUTHORIZATION_REQUEST_BASE_URI, "/") + "/" +
            OAUTH2_REGISTRATION_ID_GOOGLE;
        mav.getModel().put("googleSignInLink", googleSignInLink);

        final String loginErrorMessage = getLoginErrorMessage(session);
        mav.getModel().put("loginErrorMessage", loginErrorMessage);

        mav.setViewName("static/loginPage.th");
        return mav;
    }

    public static String getLoginErrorMessage(HttpSession session) {
        final Object securityException = session.getAttribute(AUTHENTICATION_EXCEPTION);
        if (isNull(securityException)) {
            return "false";
        }
        session.removeAttribute(AUTHENTICATION_EXCEPTION);
        if (securityException instanceof BadCredentialsException) {
            return "Incorrect username/password.";
        } else {
            return "An error occurred.";
        }
    }

}
