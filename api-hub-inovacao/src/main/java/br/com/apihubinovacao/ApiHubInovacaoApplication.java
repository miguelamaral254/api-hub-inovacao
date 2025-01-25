package br.com.apihubinovacao;

import br.com.apihubinovacao.infrastructure.conf.DotenvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiHubInovacaoApplication {

    public static void main(String[] args) {
        new DotenvConfig().loadEnv();

        SpringApplication.run(ApiHubInovacaoApplication.class, args);
    }
}
