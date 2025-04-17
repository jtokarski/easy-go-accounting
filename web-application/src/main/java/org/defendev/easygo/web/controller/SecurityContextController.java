package org.defendev.easygo.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.domain.iam.service.api.IDiscloseSecurityContextService;
import org.defendev.common.domain.iam.service.dto.ISecurityContextDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class SecurityContextController extends ApiBaseController {

    private static final Logger log = LogManager.getLogger();

    private final IDiscloseSecurityContextService discloseSecurityContextService;

    @Autowired
    public SecurityContextController(IDiscloseSecurityContextService discloseSecurityContextService) {
        this.discloseSecurityContextService = discloseSecurityContextService;
    }

    @RequestMapping(path = {"security-context"}, method = RequestMethod.GET)
    public ResponseEntity<ISecurityContextDto> getSecurityContext(CsrfToken csrfToken) {
        /*
         * The way of passing CSRF token to client application inspired by:
         *   https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html#csrf-integration-javascript-other
         */
        return ResponseEntity.ok()
            .header(csrfToken.getHeaderName(), csrfToken.getToken())
            .body(discloseSecurityContextService.getSecurityContext());
    }

}
