package com.palindrome.check;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.palindrome.check.domain.PalindromeCheckInput;
import com.palindrome.check.repo.PalindromeFileWriterRepo;
import com.palindrome.check.repo.PalindromeRepo;
import com.palindrome.check.service.PalindromeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Import(PalindromeCheckerApplicationTests.OverrideBeans.class)
@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@AutoConfigureMockMvc
class PalindromeCheckerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PalindromeService palindromeService;

	@Autowired
	private PalindromeRepo palindromeFileWriterRepo;

	@BeforeEach
	void before() {

	}

	@Test
	void testCheckIfPalindromeWorksForHappyValues() throws Exception {

		PalindromeCheckInput checkInput = PalindromeCheckInput.builder().input("ars").build();
		MvcResult result = mockMvc.perform(post("/palindrome/check")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(checkInput)))
				.andExpect(status().isOk())
				.andReturn();
		String content = result.getResponse().getContentAsString();
		Assertions.assertEquals("{\"palindrome\":false}", content);

		result = mockMvc.perform(post("/palindrome/check")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(getCheckInput("ara"))))
				.andExpect(status().isOk())
				.andReturn();
		content = result.getResponse().getContentAsString();
		Assertions.assertEquals("{\"palindrome\":true}", content);

	}

	@Test
	public void testCheckIfPalindromeThrowsExceptionForBlankSpaces() throws Exception {

		PalindromeCheckInput checkInput = getCheckInput("ar s");
		mockMvc.perform(post("/palindrome/check")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(checkInput)))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

	}

	private PalindromeCheckInput getCheckInput(String checkInput) {
		return PalindromeCheckInput.builder().input(checkInput).build();
	}


	@TestConfiguration
	public static class OverrideBeans {

		@Bean
		@Primary
		public PalindromeRepo palindromeFileWriterRepo() {
			Path tempDirPath;
			try {
				tempDirPath = Files.createTempDirectory("tempfolder");
			} catch (IOException e) {
				tempDirPath = Paths.get("/tmp/testing");
			}
			tempDirPath.toFile().deleteOnExit();
			return new PalindromeFileWriterRepo(tempDirPath.toAbsolutePath().toString());
		}
	}
}
