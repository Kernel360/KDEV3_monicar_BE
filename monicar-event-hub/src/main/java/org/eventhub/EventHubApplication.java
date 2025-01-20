package org.eventhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.eventhub", "org.common"})
public class EventHubApplication {
	public static void main(String[] args) {
		SpringApplication.run(EventHubApplication.class, args);
	}
}
