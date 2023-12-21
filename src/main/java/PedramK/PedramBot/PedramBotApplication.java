package PedramK.PedramBot;

import com.sun.tools.javac.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class for the PedramBot application.
 * <p>
 * This class serves as the entry point for the Spring Boot application.
 * It initializes logging using the java.util.logging framework, configuring a FileHandler for log output.
 * @author Pedram Kalami
 */
@SpringBootApplication
public class PedramBotApplication {
	// Logger for logging application events
	public static Logger logger = Logger.getLogger(Main.class.getName());
	public static void main(String[] args) {
		SpringApplication.run(PedramBotApplication.class, args);

			try {
				FileHandler fh = new FileHandler("LogApp.log");
				logger.addHandler(fh);
			} catch (SecurityException e) {
				logger.log(Level.SEVERE, "Failed to create log file due to security policy.", e);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Failed to create log file due to I/O error.", e);
			}


	}
}
