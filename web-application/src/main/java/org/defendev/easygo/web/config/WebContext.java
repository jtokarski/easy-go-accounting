package org.defendev.easygo.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import org.defendev.common.jackson.DefendevCommonDtoModule;
import org.defendev.common.jackson.DefendevJavaTimeModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
@EnableWebMvc
public class WebContext implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/**")
            .addResourceLocations("classpath:/view-resources/efa-app/", "classpath:/view-resources/static/")
            .resourceChain(false)
            .addResolver(new MultiSpaResourceResolver());
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        final int jacksonIndex = Iterables.indexOf(converters,
            (HttpMessageConverter<?> converter) -> (converter instanceof MappingJackson2HttpMessageConverter)
        );
        final MappingJackson2HttpMessageConverter alternateJackson = new MappingJackson2HttpMessageConverter();
        alternateJackson.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new DefendevCommonDtoModule());
        objectMapper.registerModule(new DefendevJavaTimeModule());
        alternateJackson.setObjectMapper(objectMapper);
        converters.set(jacksonIndex, alternateJackson);
    }

}
