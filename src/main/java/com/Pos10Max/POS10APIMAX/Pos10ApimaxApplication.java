package com.Pos10Max.POS10APIMAX;

import com.Pos10Max.POS10APIMAX.Security.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Pos10ApimaxApplication {

	public static void main(String[] args) {
		SpringApplication.run(Pos10ApimaxApplication.class, args);
	}

	@Bean(name="AppProperties")
	public AppProperties getAppProperties()
	{
		return new AppProperties();
	}
}
