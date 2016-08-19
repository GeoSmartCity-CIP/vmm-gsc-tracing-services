package be.vmm.gsc.tracing.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:tracing.spring.xml")
public class AppConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(AppConfiguration.class, args);
    }
}
