package kg.tasksystem;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskManagementSystemApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
				.load();

		System.setProperty("SERVER_PORT", dotenv.get("SERVER_PORT"));
		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("DB_DRIVER_CLASS_NAME", dotenv.get("DB_DRIVER_CLASS_NAME"));

		SpringApplication.run(TaskManagementSystemApplication.class, args);
	}
}