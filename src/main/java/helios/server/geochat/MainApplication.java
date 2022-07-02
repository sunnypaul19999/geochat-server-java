package helios.server.geochat;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {

	@Autowired
	EntityManager entityManager;

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
}
