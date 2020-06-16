package com.cynthia.socmed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SocmedApplication  {

	@PostConstruct
	public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));   // It will set UTC timezone
		System.out.println("Spring boot application running in UTC timezone :"+new Date());   // It will print UTC timezone
	}
	public static void main(String[] args) {


		SpringApplication.run(SocmedApplication.class, args);


	}

}
