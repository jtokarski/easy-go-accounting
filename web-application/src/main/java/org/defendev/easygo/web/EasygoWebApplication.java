package org.defendev.easygo.web;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;



@SpringBootApplication
public class EasygoWebApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EasygoWebApplication.class)
            .web(WebApplicationType.SERVLET)
            .profiles("runJar")
            .run(args);
    }

}
