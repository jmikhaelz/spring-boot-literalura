package mx.aluras.literatura.gutendex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class GutendexApplication {

	public static void main(String[] args) {
		SpringApplication.run(GutendexApplication.class, args);
	}

}
