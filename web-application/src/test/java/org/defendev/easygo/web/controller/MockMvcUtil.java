package org.defendev.easygo.web.controller;

import org.defendev.easygo.domain.iam.service.dto.EasygoRoles;
import org.defendev.easygo.domain.iam.service.dto.EasygoUserDetails;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;
import java.util.Set;


public class MockMvcUtil {

    public static Authentication mockEasygoUserDetailsAuthn() {
        final EasygoUserDetails easygoUserDetails = new EasygoUserDetails("john@example.com", "johnpass",
            Set.of(EasygoRoles.fullyAuthenticated()), 1, Map.of());
        return new TestingAuthenticationToken(easygoUserDetails, null, List.of());
    }

}
