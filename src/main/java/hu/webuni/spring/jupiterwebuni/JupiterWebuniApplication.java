package hu.webuni.spring.jupiterwebuni;

import hu.webuni.spring.jupiterwebuni.service.InitDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@RequiredArgsConstructor
@EnableScheduling
@EnableCaching
@SpringBootApplication
public class JupiterWebuniApplication implements CommandLineRunner {

    private final InitDbService initDbService;

    public static void main(String[] args) {
        SpringApplication.run(JupiterWebuniApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initDbService.deleteDb();
        initDbService.deleteAudTables();
        initDbService.addInitData();
        initDbService.modifyCourse();
    }
}
