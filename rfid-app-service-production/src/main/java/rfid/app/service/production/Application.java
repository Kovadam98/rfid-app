package rfid.app.service.production;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "rfid.app.service")
@EnableJpaRepositories(basePackages = "rfid.app.service.common")
@EntityScan(basePackages = "rfid.app.service.common")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
