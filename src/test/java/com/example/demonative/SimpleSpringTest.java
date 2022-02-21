package com.example.demonative;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Stephane Nicoll
 */
@SpringJUnitConfig
public class SimpleSpringTest {

	@Autowired
	String testBean;

	@Test
	void test() {
		assertThat(testBean).isEqualTo("hello");
	}

	@Configuration
	static class TestConfiguration {

		@Bean
		String testBean() {
			return "hello";
		}
	}
}
