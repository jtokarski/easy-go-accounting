package org.defendev.easygo.web.controller;

import org.defendev.easygo.domain.iam.service.dto.EasygoUserDetails;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;


public class MockMvcUtil {

    public static Authentication mockEasygoUserDetailsAuthn() {
        return new TestingAuthenticationToken(
            new EasygoUserDetails(1, "john@example.com", "johnpass", Map.of()),
            null,
            List.of()
        );
    }

}
