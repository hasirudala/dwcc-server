package in.hasirudala.dwcc.server.framework;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class StaticResourceConfiguration implements WebMvcConfigurer {


    @Value("${dwcc.staticFiles.path}")
    private String staticPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (staticPath != null) {
            registry
                .addResourceHandler("/**")
                .addResourceLocations("file:" + staticPath);
        }
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry
            .addViewController("/")
            .setViewName("forward:/index.html");
    }
}
