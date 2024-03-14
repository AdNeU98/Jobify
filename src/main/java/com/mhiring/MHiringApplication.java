package com.mhiring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan( basePackages = {"com.mhiring.pojo"} )
public class MHiringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MHiringApplication.class, args);
	}

}
