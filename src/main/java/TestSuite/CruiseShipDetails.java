package TestSuite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Utils.PropertiesUtils;
import Utils.XLSXUtils;

public class CruiseShipDetails {

	public static WebDriver driver;
	public static String CruiseShip;
	public static String CruiseLine;
	public static String baseUrl;
	public static String Browser;
	public static Properties props;
	public static Select select;

	WebElement element;

	public static String passName;
	public static String crew;
	public static String launchDate;
	public static List<String> languagesList;

	@BeforeClass
	public void loadDriver() throws Exception {

		props = PropertiesUtils.getProperties("cruises_driver.properties");

		Browser = props.getProperty("browser");

		baseUrl = props.getProperty("baseurl");
		CruiseLine = props.getProperty("CruiseLine");
		CruiseShip = props.getProperty("CruiseShip");

		if (Browser.equalsIgnoreCase("Chrome")) {

			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\Drivers\\chromedriver.exe");

			ChromeOptions co = new ChromeOptions();

			co.addArguments("--disable-notifications");

			co.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			co.setExperimentalOption("useAutomationExtension", false);

			driver = new ChromeDriver(co);

		} else if (Browser.equalsIgnoreCase("MSEDGE")) {

			System.setProperty("webdriver.edge.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\Drivers\\msedgedriver.exe");

			driver = new EdgeDriver();

		} else if (Browser.equalsIgnoreCase("Firefox")) {

			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\Drivers\\geckodriver.exe");

			driver = new FirefoxDriver();

		} else {
			throw new Exception("Web Driver not supported <" + Browser + ">");
		}

		driver.manage().window().maximize();// to maximize the window
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);// applying Implicitly Wait
	}

	@Test(priority = 0)
	public void getURL() {
		driver.get(baseUrl);
//		System.out.println(baseUrl);
	}

	@Test(priority = 1)
	public void cruiseLine() {
		try {
			element = driver.findElement(By.xpath("//div[contains(text(),'Cruise Line')]"));
			element.click();
			driver.findElement(By.xpath("//div[contains(text(),'" + CruiseLine + "')]")).click();
			System.out.println("Name of the Cruise Line is : " + CruiseLine);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test(priority = 2)
	public void cruiseShip() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			element = driver.findElement(By.xpath("//*[@id='component_1']/div/div[3]/div/div[2]/div/div"));
			element.click();
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//div[contains(text(),'" + CruiseShip + "')]")));

			driver.findElement(By.xpath("//div[contains(text(),'" + CruiseShip + "')]")).click();

			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Search')]")));

			System.out.println("Name of the Cruise Ship is : " + CruiseShip);

		} catch (Exception e) {
			System.out.println(e);

		}
	}

	@Test(priority = 3)
	public void shipSearch() {
		try {

			driver.findElement(By.xpath("//button[contains(text(),'Search')]")).click();

		} catch (Exception e) {
			System.out.println(e);

		}
	}

	@Test(priority = 4)
	public void getAboutInfo() throws InterruptedException {

		try {
			List<String> handles = new ArrayList<>(driver.getWindowHandles());

			driver.switchTo().window(handles.get(1));
//			System.out.print("Title of the Page : ");
//			System.out.println(driver.getTitle());

			WebElement passCrew = driver.findElement(By.xpath("//div[contains(text(),'About')]/following::div[2]"));
			WebElement launch = driver.findElement(By.xpath("//div[contains(text(),'About')]/following::div[4]"));
			if ((!passCrew.getText().equals("")) || (!launch.getText().equals(""))) {
				String details1 = passCrew.getText();
				launchDate = launch.getText();
				String[] check = details1.split(" \\| ");
				passName = check[0].trim();
				crew = check[1].trim();
				System.out.println("Number of " + passName.split(" ")[0] + " " + passName.split(" ")[1] + "\n"
						+ "Number of " + crew + "\n" + launchDate);
			} else {
				System.out.println("No details are there");
			}
		} catch (Exception e) {
			System.out.println(e);

		}

	}

	@Test(priority = 5)
	public void scrollToReviews() {
		try {
			element = driver.findElement(
					By.xpath("//h2[contains(text(),'Reviews')]/../../following-sibling::div/div/div[1]/div"));

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", element);

		} catch (Exception e) {
			System.out.println(e);

		}
	}

	@Test(priority = 6)
	public void getLanguages() {
		languagesList = new ArrayList<>();
		try {
			WebElement languages = driver.findElement(By.xpath(
					"//h2[contains(text(),'Reviews')]/../../following-sibling::div/div/div[1]/div[1]/div[3]/ul"));
			if (!languages.getText().equals("")) {
				String lang = languages.getText();
				String[] check = lang.split("[\r\n]+");
				String[] check2;
				System.out.println("The languages offered are:");
				for (int i = 1; i < check.length; i++) {
					check2 = check[i].split("\\(");
					System.out.println(check2[0]);

					languagesList.add(check2[0]);
				}
			} else {
				System.out.println("NO languages offered");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test(priority = 7)
	public void writeDetailsToExcelFile() throws Exception {

		String languages = "";
		for (String lang : languagesList) {
			languages += lang + ",";
		}
//		System.out.println(languages);
		String[][] details = new String[2][];
		details[0] = new String[] { "Cruise Line", "Cruise Ship", "No. of Passengers", "Number of Crew", "Launch Date",
				"Languages" };
		details[1] = new String[] { CruiseLine, CruiseShip, passName.split(" ")[1], crew.split(" ")[1],
				launchDate.split(" ")[1], languages };

		XLSXUtils.createWorkbook();
		XLSXUtils.createSheet("Cruise Ship Details");

		int rownum = 0;
		for (String[] det : details) {
			XLSXUtils.insertArrayToRow(rownum++, det);
		}

		XLSXUtils.saveWorkbookToFile("CruiseShipDetails");
	}

	@AfterClass
	public void quitDriver() {// quit the browser
		driver.quit();
	}

}
