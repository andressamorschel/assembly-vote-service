package com.assembly.vote.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AssemblyVoteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssemblyVoteServiceApplication.class, args);
	}

}