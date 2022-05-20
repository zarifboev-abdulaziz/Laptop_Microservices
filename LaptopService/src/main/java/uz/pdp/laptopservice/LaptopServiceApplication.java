package uz.pdp.laptopservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "uz.pdp.clients")
public class LaptopServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LaptopServiceApplication.class, args);
    }
}
