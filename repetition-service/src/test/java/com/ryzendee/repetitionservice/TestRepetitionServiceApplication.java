package com.ryzendee.repetitionservice;

import org.springframework.boot.SpringApplication;

public class TestRepetitionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(RepetitionServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
