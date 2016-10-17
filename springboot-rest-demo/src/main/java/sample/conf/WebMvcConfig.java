package sample.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	
        registry.addViewController("/").setViewName("welcom");
        registry.addViewController("/welcom").setViewName("welcom");
        
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/error").setViewName("login");
        registry.addViewController("/accessdeny").setViewName("accessdeny");
        
        registry.addViewController("/test").setViewName("test");
    }
}