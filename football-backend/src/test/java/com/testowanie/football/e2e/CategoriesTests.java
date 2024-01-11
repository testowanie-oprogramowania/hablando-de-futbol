package com.testowanie.football.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoriesTests {
	private static final String BASE_URL = "http://localhost:4200";

	private static final ChromeOptions chromeOptions = new ChromeOptions();
	final WebDriver driver = new ChromeDriver(chromeOptions);
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
	JavascriptExecutor executor = (JavascriptExecutor) driver;
	Dimension dimension = new Dimension(1000, 900);

	@BeforeAll
	static void setUpCrudAutomatedTests() {
		WebDriverManager.chromedriver().setup();
		chromeOptions.addArguments("--headless");
		chromeOptions.addArguments("--disable-gpu");
	}

	@BeforeEach
	void setUp() {
		driver.get(BASE_URL);
		driver.manage().window().setSize(dimension);
	}
	@AfterEach
	void tearDown() {
		driver.quit();
	}

	@Test
	@Order(1)
	void givenCategoryData_whenCreateCategory_thenCategoryIsCreated() throws InterruptedException, IOException {
		final var categoryName = "Sigma";

		final var categoriesButton = driver.findElement(By.cssSelector("[data-testid='categoryListButton']"));
		categoriesButton.click();

		final var createCategoryButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='addCategoryButton']")));
		createCategoryButton.click();

		final var categoryNameInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='categoryNameInput']")));
		categoryNameInput.sendKeys(categoryName);

		final var saveCategoryButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='categorySaveButton']")));
		saveCategoryButton.click();

		final var categoryListButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='categoryListButton']")));
		categoryListButton.click();

		final var categoryNames = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-testid='categoryNameSigma']")));
		assertThat(categoryNames.isDisplayed()).isTrue();
	}

	@Test
	@Order(2)
	void givenSomeCategory_whenUpdateCategory_thenCategoryIsUpdated() throws InterruptedException, IOException {
		// final var categoryName = "Sigma";
		final var newCategoryName = "literallyme";

		final var categoriesButton = driver.findElement(By.cssSelector("[data-testid='categoryListButton']"));
		categoriesButton.click();
		final var categoryMoreButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='categoryMoreButtonSigma']")));
		categoryMoreButton.click();
		final var categoryEditButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='editCategoryButton']")));
		categoryEditButton.click();
		final var categoryNameInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='categoryNameInput']")));
		categoryNameInput.clear();
		categoryNameInput.sendKeys(newCategoryName);
		final var saveCategoryButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='categorySaveButton']")));
		saveCategoryButton.click();

		Thread.sleep(1000);

		driver.navigate().refresh();
		wait.until(ExpectedConditions.urlToBe(BASE_URL + "/home"));


		final var newCategoriesButton = driver.findElement(By.cssSelector("[data-testid='categoryListButton']"));
		newCategoriesButton.click();

		Thread.sleep(1000);

		final var categoryNames = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-testid='categoryNameliterallyme']")));
		assertThat(categoryNames.isDisplayed()).isTrue();

	}

	@Test
	@Order(3)
	void givenSomeCategory_whenDeleteCategory_thenCategoryIsDeleted() throws InterruptedException, IOException {
		final var categoriesButton = driver.findElement(By.cssSelector("[data-testid='categoryListButton']"));
		categoriesButton.click();
		final var categoryMoreButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='categoryMoreButtonliterallyme']")));
		categoryMoreButton.click();
		final var categoryDeleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='deleteCategoryButton']")));
		categoryDeleteButton.click();
		final var confirmDeleteCategoryButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='confirmDeleteCategoryButton']")));
		confirmDeleteCategoryButton.click();

		Thread.sleep(1000);

		driver.navigate().refresh();
		wait.until(ExpectedConditions.urlToBe(BASE_URL + "/home"));

		final var newCategoriesButton = driver.findElement(By.cssSelector("[data-testid='categoryListButton']"));
		newCategoriesButton.click();

		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='addCategoryButton']")));

		Thread.sleep(1000);

		List<WebElement> categoryNameElements = driver.findElements(By.cssSelector("[data-testid='categoryNameliterallyme']"));

		if (categoryNameElements.isEmpty()) {
			// Element nie istnieje
			System.out.println("Element nie istnieje.");
			assertThat(true).isTrue();
		} else {
			assertThat(false).isTrue();
		}
	}
}
