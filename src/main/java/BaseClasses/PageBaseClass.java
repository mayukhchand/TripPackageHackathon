package BaseClasses;

import java.io.File;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import PageClasses.TripAdvisorLandingPage;
import Utils.PropertiesUtils;

public class PageBaseClass {

	public WebDriver driver;
	public static Properties props;
 
	public void invokeBrowser(String browsernamelocator) throws Exception {

		props = PropertiesUtils.getProperties("hotel_driver.properties");

		String browsername = props.getProperty(browsernamelocator);

		File driverfilepath = null;

		if (browsername.equalsIgnoreCase("chrome")) {
			driverfilepath = new File(System.getProperty("user.dir"),
					"src\\test\\resources\\Drivers\\chromedriver.exe");

			System.setProperty("webdriver.chrome.driver", driverfilepath.toString());
			driver = new ChromeDriver();
		} else if (browsername.equalsIgnoreCase("firefox")) {
			driverfilepath = new File(System.getProperty("user.dir"), "src\\test\\resources\\Drivers\\geckodriver.exe");

			System.setProperty("webdriver.gecko.driver", driverfilepath.toString());
			driver = new FirefoxDriver();
		} else if (browsername.equalsIgnoreCase("edge")) {
			driverfilepath = new File(System.getProperty("user.dir"),
					"src\\test\\resources\\Drivers\\msedgedriver.exe");

			System.setProperty("webdriver.edge.driver", driverfilepath.toString());
			driver = new EdgeDriver();
		} else {
			throw new Exception("Browser Name not correct: <" + browsername + ">");
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		

	}
	
	public RemoteWebDriver grid_driver;

	public void invokeBrowserGrid(String browsernamelocator) throws Exception {

		props = PropertiesUtils.getProperties("hotel_driver.properties");

		String browsername = props.getProperty(browsernamelocator);

		if (browsername.equalsIgnoreCase("chrome")) {

			DesiredCapabilities dc = DesiredCapabilities.chrome();
			grid_driver = new RemoteWebDriver(new URL("http://192.168.0.104:4444/wd/hub"), dc);

		} else if (browsername.equalsIgnoreCase("firefox")) {

			DesiredCapabilities dc = DesiredCapabilities.firefox();
			grid_driver = new RemoteWebDriver(new URL("http://192.168.0.104:4444/wd/hub"), dc);

		} else if (browsername.equalsIgnoreCase("edge")) {

			DesiredCapabilities dc = DesiredCapabilities.edge();
			grid_driver = new RemoteWebDriver(new URL("http://192.168.0.104:4444/wd/hub"), dc);

		} else {
			throw new Exception("Browser Name not correct: <" + browsername + ">");
		}
		grid_driver.manage().window().maximize();
		grid_driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	public TripAdvisorLandingPage openURL(String baseUrllocator) {
		try {
			
			if (driver != null) {
				driver.get(props.getProperty(baseUrllocator));
				return PageFactory.initElements(driver, TripAdvisorLandingPage.class);
			} else {
				grid_driver.get(props.getProperty(baseUrllocator));
				return PageFactory.initElements(grid_driver, TripAdvisorLandingPage.class);
			}
			
		} catch (Exception e) {
			
			throw e;
		}
	}

	public void teardown() {
		driver.close();
	}

	public void quitBrowser() {
		driver.quit();
	}

}
