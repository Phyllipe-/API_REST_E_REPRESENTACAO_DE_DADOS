package mdcc.sd.rest.deck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeckApplication {
	private static final Logger logger =
	LoggerFactory.getLogger(DeckApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(DeckApplication.class, args);
		logger.info("API DECK START");
	}

}
