package fr.cpe.scoobygang.atelier2.config;

import org.apache.commons.lang3.CharEncoding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class WebPageControllerConfiguration {
    @Bean
    public ClassLoaderTemplateResolver webPageTemplateResolver(){
        ClassLoaderTemplateResolver webPageTemplateResolver = new ClassLoaderTemplateResolver();

        webPageTemplateResolver.setPrefix("static/");
        webPageTemplateResolver.setSuffix(".html");
        webPageTemplateResolver.setTemplateMode("HTML");
        webPageTemplateResolver.setCharacterEncoding(CharEncoding.UTF_8);
        webPageTemplateResolver.setOrder(1);

        return webPageTemplateResolver;
    }
}
