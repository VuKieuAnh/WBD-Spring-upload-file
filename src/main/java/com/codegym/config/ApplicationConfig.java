package com.codegym.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("com.codegym.controller")
@EnableWebMvc
@PropertySource("classpath:global_config_app.properties")
public class ApplicationConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Autowired
    Environment env;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //Thymeleaf Configuration
    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        return templateResolver;
    }

    @Bean
    public TemplateEngine templateEngine(){
        TemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        return viewResolver;
    }

//    @Bean
//    public CommonsMultipartResolver multipartResolver(){
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(); multipartResolver.setMaxUploadSizePerFile(10000000);
//        return multipartResolver;
//    }
//
//    @Bean
//    public StandardServletMultipartResolver multipartResolver(){
//        return new StandardServletMultipartResolver();
//    }

    // Cấu hình để sử dụng các file nguồn tĩnh (css, image, js..)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String fileUpload = env.getProperty("file_upload").toString();

        // Image resource.
        registry.addResourceHandler("/i/**") //
                .addResourceLocations("file:" + fileUpload);

    }
}
