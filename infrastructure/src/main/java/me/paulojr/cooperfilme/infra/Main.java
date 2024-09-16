package me.paulojr.cooperfilme.infra;

import me.paulojr.cooperfilme.infra.config.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(WebServerConfig.class, args);
    }



    public Main(PasswordEncoder passwordEncoder) {
        System.out.println(passwordEncoder.encode("cooperfilmes"));

    }

}
