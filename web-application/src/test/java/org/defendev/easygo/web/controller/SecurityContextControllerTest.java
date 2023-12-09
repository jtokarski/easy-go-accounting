package org.defendev.easygo.web.controller;

import org.defendev.easygo.web.config.WebApplicationProperties;
import org.defendev.easygo.web.config.WebContext;
import org.defendev.easygo.web.config.WebSecurity;
import org.defendev.easygo.web.service.DiscloseSecurityContextService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.defendev.easygo.web.controller.MockMvcUtil.mockEasygoUserDetailsAuthn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestPropertySource(properties = {"spring.config.location=classpath:application-mockMvcTest.yaml"})
@EnableConfigurationProperties({ WebApplicationProperties.class })
@SpringJUnitWebConfig(
    classes = { WebContext.class, WebSecurity.class, EasygoControllerAdvice.class, MockRootContext.class,
        DiscloseSecurityContextService.class,
        SecurityContextController.class
    },
    initializers = { ConfigDataApplicationContextInitializer.class }
)
public class SecurityContextControllerTest {

    @Autowired
    public WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .apply(springSecurity())
            .build();
    }

    @Test
    public void returnsAnonymousSecurityContext() throws Exception {
        final MockHttpServletRequestBuilder mockRequest = get("/api/security-context")
            .with(anonymous());
        mockMvc.perform(mockRequest)
            .andExpect(status().is(200))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(header().exists("X-CSRF-TOKEN"))
            .andExpect(jsonPath("$.authentication.authorities[0].authority").value("ROLE_ANONYMOUS"))
            .andExpect(jsonPath("$.authentication.principal.username").value("anonymous"));
    }

    @Test
    public void returnsAuthenticatedSecurityContext() throws Exception {
        final MockHttpServletRequestBuilder mockRequest = get("/api/security-context")
            .with(authentication(mockEasygoUserDetailsAuthn()));
        mockMvc.perform(mockRequest)
            .andExpect(status().is(200))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(header().exists("X-CSRF-TOKEN"))
            .andExpect(jsonPath("$.authentication.authorities").isArray())
            .andExpect(jsonPath("$.authentication.principal.username").value("john@example.com"));
    }

}
