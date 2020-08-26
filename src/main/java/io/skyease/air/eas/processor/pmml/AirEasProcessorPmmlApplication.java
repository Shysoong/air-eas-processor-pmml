package io.skyease.air.eas.processor.pmml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AirEasProcessorPmmlApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirEasProcessorPmmlApplication.class, args);
	}

}
