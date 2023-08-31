package com.credix.credixhrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CredixHrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(CredixHrmApplication.class, args);
    }

}
