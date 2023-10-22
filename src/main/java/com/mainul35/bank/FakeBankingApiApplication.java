package com.mainul35.bank;

import com.mainul35.bank.init.InitializeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FakeBankingApiApplication implements CommandLineRunner {

	@Autowired
	private InitializeData initializeData;

	public static void main(String[] args) {
		SpringApplication.run(FakeBankingApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initializeData.initialize();
	}
}
