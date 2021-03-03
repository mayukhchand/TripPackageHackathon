package TestSuite;

import java.time.LocalDate;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import BaseClasses.PageBaseClass;
import PageClasses.Rentals;
import PageClasses.SearchLandingPage;
import PageClasses.TripAdvisorLandingPage;
import Utils.ExtentReportsManager;
import Utils.TakeScreenShot;
import Utils.XLSXUtils;

public class HotelDetails extends PageBaseClass {

	static PageBaseClass pbc;
	static TripAdvisorLandingPage tlp;
	static SearchLandingPage slp;
	static Rentals rentals;

	static String[][] hotelDetails;

	static ExtentReports report;
	static ExtentTest logger;

	@BeforeClass
	public void initialiseBaseClassAndInvokeBrowser() throws Exception {

		report = ExtentReportsManager.getInstance("Extracting Hotels Details");

		logger = report.createTest("Hotel_Details_Test_Case");

		try {
			pbc = new PageBaseClass();
			pbc.invokeBrowser("browsername");

			logger.log(Status.PASS, "Invoking " + props.getProperty("browsername") + " browser:SUCCESS");

		} catch (Exception e) {
			logger.log(Status.FAIL,
					"Invoking " + props.getProperty("browsername") + " browser:FAILED [EXCEPTION]:" + e.getMessage());
			
			throw e;
		}

	}

	@Test
	public void openURL() {
		try {

			tlp = pbc.openURL("baseurl");
			logger.log(Status.PASS, "Opening URL:" + props.getProperty("baseurl") + " in "
					+ props.getProperty("browsername") + ":SUCCESS");

		} catch (Exception e) {
			logger.log(Status.FAIL,
					"Opening URL:" + props.getProperty("baseurl") + ":FAILED [EXCEPTION]:" + e.getMessage());
			
			throw e;
		}
	}

	@Test(priority = 1)
	public void clickOnHolidayHome() throws Exception {
		try {

			tlp.clickOnHolidayHomes();
			logger.log(Status.INFO, "Clicked on Holiday Homes:SUCCESS");

		} catch (Exception e) {
			
			logger.log(Status.FAIL, "Holiday Homes cannot be clicked:FAILED [EXCEPTION]:" + e.getMessage());
			TakeScreenShot.takeScreenshot(tlp.driver, logger,"Holiday Homes cannot be clicked:FAILED");
			throw e;
			
		}
	}

	@Test(priority = 2)
	public void checkIfOnRentalsPage() throws Exception {
		boolean check = tlp.checkIfOnRentalsPage();

		if (check) {

			logger.log(Status.INFO, "Clicking on holiday homes navigated to Rentals page");
			searchFromRentalsPage();

		} else {

			logger.log(Status.INFO, "Clicking on holiday homes opened search text box");
			searchOption();

		}

	}

	public void searchFromRentalsPage() {
		try {

			rentals = tlp.switchTORentalsPage();

			rentals.enterSearchText(props.getProperty("searchtext"));

			logger.log(Status.INFO, props.getProperty("searchtext") + " entered in Navbar Search box:SUCCESS");

			rentals.clickOnOption(props.getProperty("searchtext"));

			logger.log(Status.INFO, props.getProperty("searchtext") + " is clicked:SUCCESS");

			slp = rentals.clickOnHolidayHome();

			logger.log(Status.INFO, "Opening Holiday Homes from rentals page:SUCCESS");

		} catch (Exception e) {

			logger.log(Status.FAIL, "Opening Holiday Homes from rentals page:FAILED [EXCEPTION]"+e.getMessage());
			TakeScreenShot.takeScreenshot(rentals.driver, logger, "Opening Holiday Homes from rentals page:FAILED");
			throw e;

		}
	}

	public void searchOption() throws Exception {

		try {

			tlp.enterSearchText(props.getProperty("searchtext"));

			logger.log(Status.INFO, props.getProperty("searchtext") + " is entered in search box:SUCCESS");

			slp = tlp.clickOnOption(props.getProperty("option"));

			logger.log(Status.INFO, props.getProperty("searchtext") + " is clicked:SUCCESS");

		} catch (Exception e) {

			logger.log(Status.FAIL, "Opening Holiday Homes using :FAILED [EXCEPTION]"+e.getMessage());
			TakeScreenShot.takeScreenshot(tlp.driver, logger, "Opening Holiday Homes using :FAILED");
			throw e;

		}
	}

	@Test(priority = 3)
	public void enterCheckInCheckOutDate() throws Exception {
		LocalDate today = LocalDate.now();
		LocalDate checkindate = today.plusDays(1);
		LocalDate checkoutdate = today.plusDays(5);
		try {
			slp.clickOnCheckInDiv();

			logger.log(Status.INFO, "Clicked on Check-In date box");

			slp.enterCheckInDate(checkindate);

			logger.log(Status.INFO, "Entered tomorrow's date in Check-In date box");

			slp.enterCheckOutDate(checkoutdate);

			logger.log(Status.INFO, "Entered check-out date in Check-Out date box");

		} catch (Exception e) {

			logger.log(Status.FAIL, "Entering CheckIn/CheckOut date:FAILED [EXCEPTION]:"+e.getMessage());
			TakeScreenShot.takeScreenshot(slp.driver, logger, "Entering CheckIn/CheckOut date:FAILED");
			throw e;
		}
	}

	@Test(priority = 4)
	public void enterNumGuests() throws Exception {
		try {

			slp.enterGuestNumsAndClickApply(4);
			logger.log(Status.INFO, "Entered number of guest");

		} catch (Exception e) {

			logger.log(Status.FAIL,"Entering number of guest:FAILED [EXCEPTION]:"+ e.getMessage());
			TakeScreenShot.takeScreenshot(slp.driver, logger, "Entering number of guest:FAILED");
			throw e;

		}
	}

	@Test(priority = 5)
	public void sortByOption() throws Exception {

		try {

			slp.sortBy(props.getProperty("sortby"));
			logger.log(Status.INFO, "Sort By option:" + props.getProperty("sortby") + " is selected:SUCCESS");

		} catch (Exception e) {

			logger.log(Status.FAIL, "Entering number of guest:FAILED [EXCEPTION]:"+ e.getMessage());
			TakeScreenShot.takeScreenshot(slp.driver, logger, "Entering number of guest:FAILED");
			throw e;

		}

	}

	@Test(priority = 6)
	public void selectAmenities() throws Exception {
		try {

			slp.selectAmenities(props.getProperty("amenities"));
			logger.log(Status.INFO, "Selected Amenities:" + props.getProperty("amenities") + ":SUCCESS");

		} catch (Exception e) {

			logger.log(Status.FAIL,"Selecting Amenities:" + props.getProperty("amenities") + ":FAILED [EXCEPTION]:"+ e.getMessage());
			TakeScreenShot.takeScreenshot(slp.driver, logger,
					"Selecting Amenities:" + props.getProperty("amenities") + ":FAILED");
			throw e;

		}

	}

	@Test(priority = 7)
	public void scrollToTop() throws Exception {

		try {

			slp.scrollToTop();
			logger.log(Status.INFO, "Scroll to top:SUCCESS");

		} catch (Exception e) {

			logger.log(Status.FAIL,"Scroll to top:FAILED [EXCEPTION]:"+ e.getMessage());
			TakeScreenShot.takeScreenshot(slp.driver, logger, "Scroll to top:FAILED");
			throw e;

		}

	}

	@Test(priority = 8)
	public void getHotelDetails() {

		try {

			int numHotels = Integer.parseInt(props.getProperty("numHotelDetails"));
			hotelDetails = slp.getHotelDetails(numHotels);
			logger.log(Status.PASS, "Getting hotel details:SUCCESS");
			TakeScreenShot.takeScreenshot(slp.driver, logger, "Hotel Details of top 3 hotels:SUCCESS");

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL,"Get Hotel Details:FAILED [EXCEPTION]"+ e.getMessage());
			TakeScreenShot.takeScreenshot(slp.driver, logger, "Get Hotel Details:FAILED");
			throw e;

		}

	}

	@Test(priority = 9)
	public void writeToXlsxFile() throws Exception {

		try {

			int rownum = 0;

			XLSXUtils.createWorkbook();
			XLSXUtils.createSheet("Hotel Details");

			for (String[] hotels : hotelDetails) {

				XLSXUtils.insertArrayToRow(rownum++, hotels);
				for (String hotel : hotels) {
					System.out.print(hotel + " ");
				}
				System.out.println();
			}

			XLSXUtils.saveWorkbookToFile("HotelDetails");
			logger.log(Status.PASS, "Writing Hotel Details to Xlsx file:SUCCESS");
		} catch (Exception e) {

			logger.log(Status.FAIL, e.getMessage());
			TakeScreenShot.takeScreenshot(slp.driver, logger, "Writing Hotel Details to Xlsx file:FAILED");
			throw e;

		}

	}

	@AfterClass
	public void quitBrowser() {
		slp.quit();
		report.flush();
	}

}
