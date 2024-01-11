package com.testowanie.football.e2e;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
@TestMethodOrder(OrderAnnotation.class)
public class ArticleTests {
	private static final String BASE_URL = "http://localhost:4200";
	private final WebDriver driver = new SeleniumConfig().getDriver();

	@BeforeEach
	void setUp() {
		driver.get(BASE_URL);
	}
	@AfterEach
	void tearDown() {
		driver.quit();
	}

	@Test
	@Order(1)
	void givenArticleData_whenCreateArticle_thenArticleIsCreated() {
		final var articleTitle = "Glik is the best";
		final var articleContent = "Glik is the GOAT (Greatest of all time)";

	}
}
