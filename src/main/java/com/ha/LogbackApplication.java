package com.ha;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogbackApplication {
	private Logger log = LoggerFactory.getLogger(LogbackApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LogbackApplication.class, args);
	}

	@PostConstruct
	public void init() {
		log.info("post construct execute!!");
		log.info("post construct execute111!!");
	}
}
