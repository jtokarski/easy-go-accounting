package org.defendev.easygo.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.domain.useridentity.service.api.IDiscloseSecurityContextService;
import org.defendev.common.domain.useridentity.service.dto.ISecurityContextDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class SecurityContextController extends ApiBaseController {

    private static final Logger log = LogManager.getLogger();

    private IDiscloseSecurityContextService discloseSecurityContextService;

    public SecurityContextController(IDiscloseSecurityContextService discloseSecurityContextService) {
        this.discloseSecurityContextService = discloseSecurityContextService;
    }

    @RequestMapping(path = {"security-context"}, method = RequestMethod.GET)
    public ResponseEntity<ISecurityContextDto> getSecurityContext() {
        return ResponseEntity.ok(discloseSecurityContextService.getSecurityContext());
    }

}