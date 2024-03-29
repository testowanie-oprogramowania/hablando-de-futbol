package com.testowanie.football.e2e;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@TestMethodOrder(OrderAnnotation.class)
public class EditorsTests {
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
	void givenEditorData_whenCreateEditor_thenEditorIsCreated() throws InterruptedException, IOException {
		final var editorName = "Trollface";
		final var editorSurname = "TROLOLOLOLO";
		final var editorPhotoUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/9/9a/Trollface_non-free.png/220px-Trollface_non-free.png";

		final var editorsButton = driver.findElement(By.cssSelector("[data-testid='editorList']"));
		executor.executeScript("arguments[0].click();", editorsButton);
		wait.until(ExpectedConditions.urlToBe(BASE_URL + "/editors"));
		final var createEditorButton = driver.findElement(By.cssSelector("[data-testid='createEditorButton']"));
		createEditorButton.click();
		wait.until(ExpectedConditions.urlToBe(BASE_URL + "/editors/create"));

		final var nameInput = driver.findElement(By.xpath("//app-text-input-field[@dataTestId='editorNameInput']//input"));
		nameInput.sendKeys(editorName);
		final var surnameInput = driver.findElement(By.xpath("//app-text-input-field[@dataTestId='editorSurnameInput']//input"));
		surnameInput.sendKeys(editorSurname);
		final var photoUrlInput = driver.findElement(By.xpath("//app-text-input-field[@dataTestId='editorPhotoUrlInput']//input"));
		photoUrlInput.sendKeys(editorPhotoUrl);

//		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//		FileUtils.copyFile(screenshot, new File("./screenshot.png"));

		final var addButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='saveEditorButton']")));
		addButton.click();
		wait.until(ExpectedConditions.urlToBe(BASE_URL + "/editors"));

		List<WebElement> editorCards = driver.findElements(By.cssSelector("[data-testid='editorCard']"));
		WebElement editorCard = null;
		for (WebElement card : editorCards) {
			String currentEditorName = card.findElement(By.cssSelector("[data-testid='editorName']")).getText();
			if (currentEditorName.equals("Trollface TROLOLOLOLO")) {
				editorCard = card;
				break;
			}
		}
		if (editorCard == null) {
			throw new RuntimeException("Editor not found");
		}

		assertThat(editorCard.isDisplayed()).isTrue();
	}

	@Test
	@Order(2)
	void givenSomeEditor_whenUpdateEditor_thenEditorIsUpdated() throws InterruptedException, IOException {
		final var editorName = "Ryan";
		final var editorSurname = "Gosling";
		final var editorPhotoUrl = "https://i1.sndcdn.com/artworks-C8MiqJ8yiIXAE5Wi-ihUPHw-t500x500.jpg";

		final var editorsButton = driver.findElement(By.cssSelector("[data-testid='editorList']"));
		executor.executeScript("arguments[0].click();", editorsButton);
		wait.until(ExpectedConditions.urlToBe(BASE_URL + "/editors"));

		final var editEditorButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='editEditorButtonTrollfaceTROLOLOLOLO']")));
		editEditorButton.click();
		wait.until(ExpectedConditions.urlMatches(BASE_URL + "/editors/[0-9]+/edit"));

		final var nameInput = driver.findElement(By.xpath("//app-text-input-field[@dataTestId='editorNameInput']//input"));
		nameInput.clear();
		nameInput.sendKeys(editorName);
		final var surnameInput = driver.findElement(By.xpath("//app-text-input-field[@dataTestId='editorSurnameInput']//input"));
		surnameInput.clear();
		surnameInput.sendKeys(editorSurname);
		final var photoUrlInput = driver.findElement(By.xpath("//app-text-input-field[@dataTestId='editorPhotoUrlInput']//input"));
		photoUrlInput.clear();
		photoUrlInput.sendKeys(editorPhotoUrl);

		final var updateButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='saveEditorButton']")));
		updateButton.click();
		wait.until(ExpectedConditions.urlToBe(BASE_URL + "/editors"));

		List<WebElement> editorCards = driver.findElements(By.cssSelector("[data-testid='editorCard']"));
		WebElement editorCardEdited = null;
		for (WebElement card : editorCards) {
			String currentEditorName = card.findElement(By.cssSelector("[data-testid='editorName']")).getText();
			if (currentEditorName.equals("Ryan Gosling")) {
				editorCardEdited = card;
				break;
			}
		}
		if (editorCardEdited == null) {
			throw new RuntimeException("Edited editor not found");
		}
//		final var editorCardEdited = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='editorCardRyanGosling']")));
		assertThat(editorCardEdited.isDisplayed()).isTrue();
	}

	@Test
	@Order(3)
	void givenSomeEditor_whenDeleteEditor_thenEditorIsDeleted() throws InterruptedException, IOException {
		final var editorsButton = driver.findElement(By.cssSelector("[data-testid='editorList']"));
		executor.executeScript("arguments[0].click();", editorsButton);
		wait.until(ExpectedConditions.urlToBe(BASE_URL + "/editors"));

		List<WebElement> editorCards = driver.findElements(By.cssSelector("[data-testid='editorCard']"));
		WebElement editorCardToDelete = null;
		for (WebElement card : editorCards) {
			String currentEditorName = card.findElement(By.cssSelector("[data-testid='editorName']")).getText();
			if (currentEditorName.equals("Ryan Gosling")) {
				editorCardToDelete = card;
				break;
			}
		}
		if (editorCardToDelete == null) {
			throw new RuntimeException("Editor to delete not found");
		}

		final var deleteEditorButton = driver.findElement(By.cssSelector("[data-testid='deleteEditorButtonRyanGosling']"));
		deleteEditorButton.click();

		wait.until(ExpectedConditions.invisibilityOf(editorCardToDelete));

		editorCards = driver.findElements(By.cssSelector("[data-testid='editorCard']"));
		editorCardToDelete = null;
		for (WebElement card : editorCards) {
			String currentEditorName = card.findElement(By.cssSelector("[data-testid='editorName']")).getText();
			if (currentEditorName.equals("Ryan Gosling")) {
				editorCardToDelete = card;
				break;
			}
		}

		assertThat(editorCardToDelete == null).isTrue();
	}



}
