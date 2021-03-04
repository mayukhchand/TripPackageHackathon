package PageClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import BaseClasses.PageBaseClass;

public class TripAdvisorLandingPage extends PageBaseClass {
	public WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor jse;
	
	public TripAdvisorLandingPage(WebDriver driver) {
		this.driver = driver;

		wait = new WebDriverWait(driver, 10);
	}

	public void clickOnHolidayHomes() throws Exception {
		
		String holidayhomelocator = "holidayhomes_Xpath";
		WebElement holidayhomesspan = getElement(holidayhomelocator);
		holidayhomesspan.click(); 
	}

	public void enterSearchText( String option) throws Exception {
		
		String searchbarlocator = "searchBar_CSS";
		
		WebElement searchBar = getElement(searchbarlocator);
		wait.until(ExpectedConditions.visibilityOf(searchBar));

		searchBar.sendKeys(option);
		
	}

	public SearchLandingPage clickOnOption( String option) throws Exception {
		
		String ajaxoptionlocator =  "ajaxoptionlocator";

		String locator = props.getProperty(ajaxoptionlocator).replaceAll("<option>", option );
		

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));

		driver.findElement(By.xpath(locator)).click();

		return PageFactory.initElements(driver, SearchLandingPage.class);
	}

	public WebElement getElement(String locator) throws Exception {
		WebElement element = null;
		try {

			if (locator.endsWith("_Xpath")) {
				element = driver.findElement(By.xpath(props.getProperty(locator)));
				
			} else if (locator.endsWith("_Id")) {
				element = driver.findElement(By.id(props.getProperty(locator)));
				
			} else if (locator.endsWith("_CSS")) {
				element = driver.findElement(By.cssSelector(props.getProperty(locator)));
				
			} else if (locator.endsWith("_Name")) {
				element = driver.findElement(By.name(props.getProperty(locator)));
				
			} else if (locator.endsWith("_TagName")) {
				element = driver.findElement(By.tagName(props.getProperty(locator)));
				
			} else if (locator.endsWith("_LinkText")) {
				element = driver.findElement(By.xpath(props.getProperty(locator)));
				
			} else if (locator.endsWith("_PartialLinkText")) {
				element = driver.findElement(By.xpath(props.getProperty(locator)));
				
			}
		} catch (Exception e) {
			
			throw new Exception(e.getMessage());
		}

		return element;
	}

	public boolean checkIfOnRentalsPage() {
		
		String pagetitle = driver.getTitle();
		
		String rentalsPageTitle = "Homestay, Holiday Rentals - Over 6,30,000 Holiday Homes - Tripadvisor";
		
		return rentalsPageTitle.equals(pagetitle);
	}
	
	public void navToRentalsPage() {
		driver.navigate().to("https://www.tripadvisor.in/Rentals");
	}

	public Rentals switchTORentalsPage() {
		
		return PageFactory.initElements(driver, Rentals.class);
		
	}

}
