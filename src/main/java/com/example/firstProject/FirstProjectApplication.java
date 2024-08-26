package com.example.firstProject;

import com.example.firstProject.Util.JwtUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FirstProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstProjectApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Gson gson() {
		return new GsonBuilder().create();
	}

  @Bean
  public BCryptPasswordEncoder crypt(){return new BCryptPasswordEncoder();}

  @Bean
  public JwtUtil jwtUtil(){return new JwtUtil();}


}
