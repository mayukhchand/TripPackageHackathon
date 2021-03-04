package PageClasses;

import java.time.LocalDate;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import BaseClasses.PageBaseClass;
import Utils.DataFormatter;

public class SearchLandingPage extends PageBaseClass {

	public WebDriver driver;
	public WebDriverWait wait;

	public SearchLandingPage(WebDriver driver) {

		this.driver = driver;
		wait = new WebDriverWait(driver, 20);
	}
 
	public void clickOnCheckInDiv() throws Exception {

		String checkinelementlocator = "checkinelement_Xpath";

		WebElement checkindateelement = getElement(checkinelementlocator);
		checkindateelement.click();
	}

	public void enterCheckInDate(LocalDate checkindate) throws Exception {

		String checkindate_Xpath = dateXpath(checkindate);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(checkindate_Xpath)));

		driver.findElement(By.xpath(checkindate_Xpath)).click();
	}

	public void enterCheckOutDate(LocalDate checkoutdate) {
		String checkoutdate_Xpath = dateXpath(checkoutdate);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(checkoutdate_Xpath)));

		driver.findElement(By.xpath(checkoutdate_Xpath)).click();
	}

	public String dateXpath(LocalDate date) {
		LocalDate today = LocalDate.now();

		int divnum = today.getMonthValue() == date.getMonthValue() ? 1 : 2;

		String dateOfMonth = dayOfTheMonth(date);

		String dateLocator = "date_Xpath";

		String date_Xpath = props.getProperty(dateLocator).replaceAll("<date>", dateOfMonth).replaceAll("<div-num>",
				String.valueOf(divnum));

		return date_Xpath;
	}

	public String dayOfTheMonth(LocalDate date) {
		String dateOfMonth = date.getDayOfMonth() < 10 ? ("0" + String.valueOf(date.getDayOfMonth()))
				: String.valueOf(date.getDayOfMonth());
		return dateOfMonth;
	}

	public void enterGuestNumsAndClickApply(int gnum) throws Exception {

		String guestelelocator = "guestElement_Xpath";

		String guestele_Xpath = props.getProperty(guestelelocator);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(guestele_Xpath)));

		WebElement guestelement = getElement(guestelelocator);
		guestelement.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(props.getProperty("guestDiv_CSS"))));

		int guestnum = getGuestNumAsInt("guestinput_CSS");

		while (guestnum < gnum) {
			driver.findElement(By.cssSelector(props.getProperty("guestaddbutton_CSS"))).click();
			guestnum = getGuestNumAsInt("guestinput_CSS");
		}

		getElement("guestapplybutton_Xpath").click();
	}

	public int getGuestNumAsInt(String locator) throws Exception {
		String numguest = getElement(locator).getAttribute("value");
		int num = Integer.parseInt(String.valueOf(numguest.charAt(0)));
		return num;
	}

	public void sortBy(String option) throws Exception {
		wait.until(
				ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(props.getProperty("loadingele_Xpath"))));

		String sortelelocator = "sortele_Xpath";

		WebElement sortelement = getElement(sortelelocator);

		sortelement.click();

		String sortoption_Xpath = props.getProperty("sortoptionele_Xpath").replaceAll("<sortoption>", option);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(sortoption_Xpath)));

		driver.findElement(By.xpath(sortoption_Xpath)).click();

	}

	public void selectAmenities(String amenitiesoption) throws Exception {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView()", getElement("amenitiesdiv_Xpath"));

		driver.findElement(By.xpath(props.getProperty("showmoreamenities_Xpath"))).click();

		String extraamenities_Xpath = props.getProperty("extraamenities_Xpath").replaceAll("<amenitiesoption>",
				amenitiesoption);

		driver.findElement(By.xpath(extraamenities_Xpath)).click();

	}

	public void scrollToTop() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView()", getElement("header_CSS"));
	}

	public String[][] getHotelDetails(int numOfhotels) {
		wait.until(ExpectedConditions
				.invisibilityOfElementLocated(By.cssSelector(props.getProperty("resultsdivloadingele_Xpath"))));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(props.getProperty("firstresultdiv_Xpath"))));

		List<WebElement> hotellist = driver.findElements(By.xpath(props.getProperty("resultsdiv_Xpath")));

		int count = 0;

		String hotelname_Xpath = props.getProperty("hotelname_Xpath");
		String pernight_Xpath = props.getProperty("pernightprice_Xpath");
		String totalcost_Xpath = props.getProperty("totalcost_Xpath");

		String[][] hotelDetails = new String[numOfhotels + 1][];

		hotelDetails[0] = new String[] { "Hotel name", "Per night price(in Rs)", "Total price for 4 nights(in Rs)" };

		for (WebElement hotel : hotellist) {

			if (count == numOfhotels) {
				break;
			}

			if (hotel.getText().contains("per night") && (!hotel.getText().equals(""))) {
				String hotelnametext = hotel.findElement(By.xpath(hotelname_Xpath)).getText();

				String pernightText = hotel.findElement(By.xpath(pernight_Xpath)).getText();
				String pernightprice = DataFormatter.formatter(pernightText);

				String totalcosttext = hotel.findElement(By.xpath(totalcost_Xpath)).getText();
				String totalcostprice = DataFormatter.formatter(totalcosttext);

				count++;
				hotelDetails[count] = new String[] { hotelnametext, pernightprice, totalcostprice };
			}
		}
		return hotelDetails;
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
			
			throw new Exception("Locator Not correct " + locator);
		}

		return element;
	}

	public void quit() {
		driver.quit();
	}

}
