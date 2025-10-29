package com.tricol.tricol;

import org.springframework.boot.SpringApplication;

public class TestTricolApplication {

	public static void main(String[] args) {
		SpringApplication.from(TricolApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
