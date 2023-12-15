package org.defendev.easygo.web.controller;

import org.defendev.easygo.web.config.WebApplicationProperties;
import org.defendev.easygo.web.config.WebContext;
import org.defendev.easygo.web.config.WebSecurity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@TestPropertySource(properties = {"spring.config.location=classpath:application-mockMvcTest.yaml"})
@EnableConfigurationProperties({ WebApplicationProperties.class })
@SpringJUnitWebConfig(
    classes = { WebContext.class, WebSecurity.class, MockRootContext.class, LastVisitedController.class },
    initializers = { ConfigDataApplicationContextInitializer.class }
)
public class LastVisitedControllerTest {

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
    public void returnsEmptyDatetimeForNewSession() throws Exception {
        mockMvc.perform(get("/api/last-visited").with(anonymous()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.lastVisited").value("-"))
            .andReturn();
    }

    @Test
    public void returnsCorrectDatetimeForExistingSession() throws Exception {
        final ZonedDateTime mockLastVisited = ZonedDateTime.of(LocalDate.of(2015, Month.APRIL, 1),
            LocalTime.of(8, 59), ZoneId.systemDefault());
        final MockHttpServletRequestBuilder mockRequest = get("/api/last-visited")
            .with(anonymous())
            .sessionAttrs(Map.of("lastVisited", mockLastVisited));
        mockMvc.perform(mockRequest)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.lastVisited").value("2015-04-01T08:59:00"))
            .andReturn();
    }

    @Test
    public void returnsCorrectDatetimeFromEstablishedSession() throws Exception {
        final MockHttpServletRequestBuilder firstMockRequest = get("/api/last-visited")
            .with(anonymous());
        final MvcResult mvcResult = mockMvc.perform(firstMockRequest)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.lastVisited").value("-"))
            .andReturn();
        final MockHttpSession establishedSession = (MockHttpSession) mvcResult.getRequest().getSession();

        final MockHttpServletRequestBuilder secondMockRequest = get("/api/last-visited")
            .with(anonymous())
            .session(establishedSession);
        mockMvc.perform(secondMockRequest)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.lastVisited").value("2023-12-15T21:48:00"))
            .andReturn();
    }

}
