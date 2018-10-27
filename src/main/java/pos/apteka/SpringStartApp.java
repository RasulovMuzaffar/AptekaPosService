package pos.apteka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication//(scanBasePackages = {"pos.apteka"})
public class SpringStartApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(SpringStartApp.class, args);
    }

//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(applicationClass);
//    }
//
//    private static Class<SpringStartApp> applicationClass = SpringStartApp.class;
}
