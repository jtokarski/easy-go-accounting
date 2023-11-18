package org.defendev.easygo.web.controller;

import org.defendev.easygo.web.config.WebApplicationProperties;
import org.defendev.easygo.web.config.WebContext;
import org.defendev.easygo.web.config.WebSecurity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;



@TestPropertySource(properties = {"spring.config.location=classpath:application-mockMvcTest.yaml"})
@EnableConfigurationProperties({ WebApplicationProperties.class })
@SpringJUnitWebConfig(
    classes = { WebContext.class, WebSecurity.class, MockRootContext.class, FormLoginPageController.class },
    initializers = { ConfigDataApplicationContextInitializer.class }
)
public class FormLoginPageControllerTest {

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
    public void returnsCustomLoginPage() throws Exception {
        mockMvc.perform(get("/sign-in").with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(view().name("static/loginPage.th"))
            .andExpect(model().attribute("azureSignInLink", "oauth2/authorization/azure"))
            .andExpect(model().attribute("googleSignInLink", "oauth2/authorization/google"))
            .andExpect(model().attribute("loginErrorMessage", "false"))
            .andExpect(unauthenticated())
            .andReturn();
    }

}
