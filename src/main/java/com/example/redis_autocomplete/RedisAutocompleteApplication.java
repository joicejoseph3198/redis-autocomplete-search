package com.example.redis_autocomplete;

import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRedisDocumentRepositories
public class RedisAutocompleteApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisAutocompleteApplication.class, args);
	}

}
