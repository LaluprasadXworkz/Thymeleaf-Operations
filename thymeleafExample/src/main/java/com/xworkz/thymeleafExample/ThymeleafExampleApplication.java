package com.xworkz.thymeleafExample;

import com.xworkz.thymeleafExample.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@SpringBootApplication
public class ThymeleafExampleApplication {

	@Autowired
	private static VehicleService vehicleService;

	public static void main(String[] args) {
		SpringApplication.run(ThymeleafExampleApplication.class, args);
	}
}
