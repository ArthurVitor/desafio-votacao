package com.dbserver.votacao;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
public class SicrediVotacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SicrediVotacaoApplication.class, args);
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("UTC-3")));
	}

}
