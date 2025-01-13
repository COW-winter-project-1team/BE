package project.moodipie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MoodiPieApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoodiPieApplication.class, args);
    }

}
