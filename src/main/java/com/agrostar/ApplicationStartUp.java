package com.agrostar;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
 
/**
 * @author Bhaskar Ghosh
 *
 */
@SpringBootApplication
@EnableJpaAuditing
public class ApplicationStartUp extends SpringBootServletInitializer
{
    public static void main(String[] args)
    {
        new ApplicationStartUp().configure(new SpringApplicationBuilder(ApplicationStartUp.class)).run(args);
    }
}