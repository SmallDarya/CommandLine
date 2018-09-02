package com.smolienko.commandline.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 *
 * @author Darya Smolienko
 */
@Configuration
public class AppConfig {
        
    @Bean
    public MessageSource messageSource() {
    	ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("locale.messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
