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

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Utils.ExtentReportsManager;
import Utils.PropertiesUtils;
import Utils.TakeScreenShot;
import Utils.XLSXUtils;

public class CruiseShipDetails {

	static WebDriver driver;
	static String CruiseShip;
	static String CruiseLine;
	static String baseUrl;
	static String Browser;
	static Properties props;
	static Select select;

	WebElement element; 

	static String passName;
	static String crew;
	static String launchDate;
	static List<String> languagesList;
	
	static ExtentReports report;
	static ExtentTest logger;

	@BeforeClass
	public void loadDriver() throws Exception {
		
		report = ExtentReportsManager.getInstance("Extracting Cruise Ship Details");

		logger = report.createTest("Cruise_Ship_Details_Test_Case");
		
		try {
			
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
				
				logger.log(Status.PASS, "Invoked Chrome browser");

			} else if (Browser.equalsIgnoreCase("MSEDGE")) {

				System.setProperty("webdriver.edge.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\Drivers\\msedgedriver.exe");

				driver = new EdgeDriver();

				logger.log(Status.PASS, "Invoked MsEdge browser");
				
			} else if (Browser.equalsIgnoreCase("Firefox")) {

				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\Drivers\\geckodriver.exe");

				driver = new FirefoxDriver();

				logger.log(Status.PASS, "Invoked Firefox browser");

			} else {
				throw new Exception("Web Driver not supported <" + Browser + ">");
			}

			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
		} catch (Exception e) {
			
			logger.log(Status.FAIL, "Opening Browser:[FAILED] [EXCEPTION]"+e.getMessage());
			
			throw e;
			
		}

	}

	@Test(priority = 0)
	public void getURL() {
		
		try {
			
			driver.get(baseUrl);
			
		} catch (Exception e) {
			logger.log(Status.FAIL, "Opening URL"+baseUrl+"[FALIED] [EXCEPTION]:"+e.getMessage());
			
			throw e;
		}
		
		
	}

	@Test(priority = 1)
	public void cruiseLine() {
		try {
			element = driver.findElement(By.xpath("//div[contains(text(),'Cruise Line')]"));
			element.click();
			driver.findElement(By.xpath("//div[contains(text(),'" + CruiseLine + "')]")).click();
			System.out.println("Name of the Cruise Line is : " + CruiseLine);
			logger.log(Status.PASS, "Selecting Cruise Line:SUCCESS");
		} catch (Exception e) {

			logger.log(Status.FAIL, "Selecting Cruise Line:FAILED [EXCEPTION]:" + e.getMessage());
			TakeScreenShot.takeScreenshot(driver, logger,"Selecting Cruise Line:FAILED");
			throw e;
			
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
			logger.log(Status.PASS, "Selecting Cruise Ship:SUCCESS");
		} catch (Exception e) {

			logger.log(Status.FAIL, "Selecting Cruise Ship:FAILED [EXCEPTION]:" + e.getMessage());
			TakeScreenShot.takeScreenshot(driver, logger,"Selecting Cruise Ship:FAILED");
			throw e;
			
		}
	}

	@Test(priority = 3)
	public void shipSearch() {
		try {

			driver.findElement(By.xpath("//button[contains(text(),'Search')]")).click();
			logger.log(Status.INFO, "Clicked on Search button:SUCCESS");
		} catch (Exception e) {

			logger.log(Status.FAIL, "Clicking on Search button:FAILED [EXCEPTION]:" + e.getMessage());
			TakeScreenShot.takeScreenshot(driver, logger,"Clicking on Search button:FAILED");
			throw e;
			
		}
	}

	@Test(priority = 4)
	public void getAboutInfo() throws InterruptedException {

		try {
			List<String> handles = new ArrayList<>(driver.getWindowHandles());

			driver.switchTo().window(handles.get(1));
			
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
			
			logger.log(Status.PASS, "Cruise ship Info retrived:SUCCESS");
			TakeScreenShot.takeScreenshot(driver, logger, "Cruise Ship details");
		} catch (Exception e) {

			logger.log(Status.FAIL, "Retriving Cruise ship Info:FAILED [EXCEPTION]:" + e.getMessage());
			TakeScreenShot.takeScreenshot(driver, logger,"Retriving Cruise ship Info:FAILED");
			throw e;

		}

	}

	@Test(priority = 5)
	public void scrollToReviews() {
		try {
			element = driver.findElement(
					By.xpath("//h2[contains(text(),'Reviews')]/../../following-sibling::div/div/div[1]/div"));

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", element);
			
			logger.log(Status.PASS, "Scroll to Reviews:SUCCESS");

		} catch (Exception e) {

			logger.log(Status.FAIL, "Scroll to Reviews:FAILED [EXCEPTION]:" + e.getMessage());
			TakeScreenShot.takeScreenshot(driver, logger,"Scroll to Reviews:FAILED");
			throw e;

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
			
			logger.log(Status.PASS, "Getting Languages:SUCCESS");
			TakeScreenShot.takeScreenshot(driver, logger,"Languages Offered");

		} catch (Exception e) {

			logger.log(Status.FAIL, "Getting Languages:FAILED [EXCEPTION]:" + e.getMessage());
			TakeScreenShot.takeScreenshot(driver, logger,"Getting Languages:FAILED");
			throw e;
			
		}
	}

	@Test(priority = 7)
	public void writeDetailsToExcelFile() throws Exception {
		
		try {

			String languages = "";
			for (String lang : languagesList) {
				languages += lang + ",";
			}

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
			
			logger.log(Status.PASS, "Writing to Excle file:SUCCESS");
		
		} catch (Exception e) {
			
			logger.log(Status.FAIL, "Writing to Excle file:FAILED [EXCEPTION]:" + e.getMessage());
			TakeScreenShot.takeScreenshot(driver, logger,"Writing to Excle file:FAILED");
			throw e;
			
		}
		
	}

	@AfterClass
	public void quitDriver() {// quit the browser
		driver.quit();
		report.flush();
	}

}
