package TestSuite;

import java.time.LocalDate;

import org.testng.annotations.*;

import BaseClasses.PageBaseClass;
import PageClasses.Rentals;
import PageClasses.SearchLandingPage;
import PageClasses.TripAdvisorLandingPage;
import Utils.XLSXUtils;

public class HotelDetails extends PageBaseClass {

	static PageBaseClass pbc;
	static TripAdvisorLandingPage tlp;
	static SearchLandingPage slp;
	static Rentals rentals;

	static String[][] hotelDetails;

//	@BeforeTest
//	public void initialiseBaseClass() {
//		pbc = new PageBaseClass();
//	}

	@BeforeClass
	public void initialiseBaseClassAndInvokeBrowser() throws Exception {
		pbc = new PageBaseClass();
		pbc.invokeBrowser("browsername");
	}

	@Test
	public void openURL() {
		tlp = pbc.openURL("baseurl");
	}
	
	@Test(priority = 1)
	public void clickOnHolidayHome() throws Exception {
		tlp.clickOnHolidayHomes();
		
		//tlp.checkIfOnRentalsPage();
		
//		tlp.navToRentalsPage();
		
	}
	
	@Test(priority = 2)
	public void checkIfOnRentalsPage() throws Exception {
	 	boolean check =  tlp.checkIfOnRentalsPage();
	 	
	 	
	 	if(check) {
//	 		System.out.println("Yes");
	 		searchFromRentalsPage();
	 	}else {
//	 		searchUsingSearchBar();
	 		searchOption();
	 	}
	 	
	}
	

	public void searchFromRentalsPage() {
		rentals = tlp.switchTORentalsPage();
		rentals.enterSearchText(props.getProperty("searchtext"));
		rentals.clickOnOption(props.getProperty("searchtext"));
		
		slp =  rentals.clickOnHolidayHome();
	}

//	public void searchUsingSearchBar() throws Exception {
//		searchOption();
//	}

	public static void searchOption() throws Exception {
		

		tlp.enterSearchText(props.getProperty("searchtext"));

		slp = tlp.clickOnOption(props.getProperty("option"));
	}

	@Test(priority = 3)
	public void enterCheckInCheckOutDate() throws Exception {
		LocalDate today = LocalDate.now();
		LocalDate checkindate = today.plusDays(1);
//		System.out.println(checkindate);
		LocalDate checkoutdate = today.plusDays(5);
//		System.out.println(checkoutdate);

		slp.clickOnCheckInDiv();

		slp.enterCheckInDate(checkindate);

		slp.enterCheckOutDate(checkoutdate);
	}

	@Test(priority = 4)
	public void enterNumGuests() throws Exception {
		slp.enterGuestNumsAndClickApply(4);
	}

	@Test(priority = 5)
	public void sortByOption() throws Exception {
		slp.sortBy(props.getProperty("sortby"));
	}

	@Test(priority = 6)
	public void selectAmenities() throws Exception {
		slp.selectAmenities(props.getProperty("amenities"));
	}

	@Test(priority = 7)
	public void scrollToTop() throws Exception {
		slp.scrollToTop();
	}
	

	@Test(priority = 8)
	public void getHotelDetails() {
		
		int numHotels = Integer.parseInt(props.getProperty("numHotelDetails"));
		hotelDetails = slp.getHotelDetails(numHotels);
	}
	
	@Test(priority = 9 )
	public void writeToXlsxFile() throws Exception {

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
	}
	

	@AfterClass
	public void quitBrowser() {
		slp.quit();
	}

}
