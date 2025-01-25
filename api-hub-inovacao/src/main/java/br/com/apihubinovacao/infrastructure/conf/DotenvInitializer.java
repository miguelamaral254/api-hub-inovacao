package br.com.apihubinovacao.infrastructure.conf;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DotenvInitializer {

    @PostConstruct
    public void loadEnv() {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();

        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
            System.out.println("Loaded env: " + entry.getKey() + "=" + entry.getValue());
        });
        boolean showInfo = false;

        if (showInfo) {
            System.out.println("DEBUG INFO - Variáveis principais carregadas:");
            System.out.println("DATABASE_URL: " + System.getProperty("DATABASE_URL"));
            System.out.println("DATABASE_USER: " + System.getProperty("DATABASE_USER"));
            System.out.println("DATABASE_PASSWORD: " + System.getProperty("DATABASE_PASSWORD"));
        }
    }
}
