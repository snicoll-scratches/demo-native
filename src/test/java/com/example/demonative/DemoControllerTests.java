package com.example.demonative;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DemoController.class)
class DemoControllerTests {

	private final MockMvc mvc;

	public DemoControllerTests(@Autowired MockMvc mvc) {
		this.mvc = mvc;
	}

	@Test
	void demo() throws Exception {
		this.mvc.perform(get("/demo")).andExpect(content().string("demo")).andExpect(status().isOk());
	}

}
