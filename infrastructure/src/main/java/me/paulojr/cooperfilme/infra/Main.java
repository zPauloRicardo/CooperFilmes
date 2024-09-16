package me.paulojr.cooperfilme.infra;

import me.paulojr.cooperfilme.infra.config.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(WebServerConfig.class, args);
    }


}
