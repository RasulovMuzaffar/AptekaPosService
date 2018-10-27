package pos.apteka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication//(scanBasePackages = {"pos.apteka"})
public class SpringStartApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringStartApp.class, args);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    private static Class<SpringStartApp> applicationClass = SpringStartApp.class;
}
