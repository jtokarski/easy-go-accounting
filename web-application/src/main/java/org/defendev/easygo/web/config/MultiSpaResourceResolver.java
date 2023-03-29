package org.defendev.easygo.web.config;

import java.io.IOException;
import java.util.regex.Matcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.PathResourceResolver;


public class MultiSpaResourceResolver extends PathResourceResolver {

    private static final Logger log = LogManager.getLogger();

    @Override
    protected Resource getResource(String resourcePath, Resource location) throws IOException {

        // todo: AllowedResourcePathMatchers (which is mainly the purpose of having this class...)

        return super.getResource(resourcePath, location);
    }

}
