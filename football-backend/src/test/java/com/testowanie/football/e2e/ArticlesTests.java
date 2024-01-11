package com.testowanie.football.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ArticlesTests {
	private static final String BASE_URL = "http://localhost:4200";

	private static final ChromeOptions chromeOptions = new ChromeOptions();
	final WebDriver driver = new ChromeDriver(chromeOptions);
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
	JavascriptExecutor executor = (JavascriptExecutor) driver;
	@BeforeAll
	static void setUpCrudAutomatedTests() {
		WebDriverManager.chromedriver().setup();
		chromeOptions.addArguments("--headless");
		chromeOptions.addArguments("--disable-gpu");
	}

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
	void tst() {
		Assertions.assertTrue(true);
	}
}
