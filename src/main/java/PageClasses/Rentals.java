package PageClasses;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import BaseClasses.PageBaseClass;

public class Rentals extends PageBaseClass {
	public WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor jse;
	
	public Rentals(WebDriver driver) {
		this.driver = driver;
		
		wait = new WebDriverWait(driver, 10);
	}
	 
	public void enterSearchText(String text) {
		String locator = props.getProperty( "rentalsSearchBar_Xpath") ;
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		driver.findElement(By.xpath( locator ) ).sendKeys(text);
	}
	
	public void clickOnOption(String option) {
		String ajaxoptionlocator =  "rentalsAjax_Xpath";

		String locator = props.getProperty(ajaxoptionlocator).replaceAll("<option>", option );
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));

		driver.findElement(By.xpath(locator)).click();
	}
	
	public SearchLandingPage clickOnHolidayHome() {
		
		String locator = props.getProperty("rentalsHolidayHomes_Xpath");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));

		driver.findElement(By.xpath(locator)).click();
		
		return PageFactory.initElements(driver, SearchLandingPage.class);
	}
	
}
