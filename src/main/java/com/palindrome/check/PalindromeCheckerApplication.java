package com.palindrome.check;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class PalindromeCheckerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PalindromeCheckerApplication.class, args);
	}

}
