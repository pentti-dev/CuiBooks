package vn.edu.hcmuaf.fit.fahabook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ConfigurationPropertiesScan("vn.edu.hcmuaf.fit.fahabook.config")
public class FahabookApplication {

    public static void main(String[] args) {
        SpringApplication.run(FahabookApplication.class, args);
    }

}
