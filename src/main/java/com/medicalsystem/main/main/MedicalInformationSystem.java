package com.medicalsystem.main.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"com.hotel.royalpalace.model", "com.hotel.royalpalace.main", "controller",
		"com.hotel.royalpalace.service"})
@EntityScan(basePackages = {"com.hotel.royalpalace.model"})
@EnableScheduling
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class MedicalInformationSystem {

	public static void main(String[] args) {
		SpringApplication.run(MedicalInformationSystem.class, args);
	}
}
