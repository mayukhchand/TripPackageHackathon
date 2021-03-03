package Utils;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;

public class ExtentReportsManager {
	  
	 static ExtentReports report;
	  
	 public static ExtentReports getInstance(String documentTitle) {
		 String filename = DateUtils.getDateString()+".html";
		 
		 if( report ==null) {
			 ExtentHtmlReporter htmlreporter = new ExtentHtmlReporter(
					 new File(System.getProperty("user.dir"),"Test-Report//"+filename));
			 
			 report = new ExtentReports();
			 
			 report.attachReporter(htmlreporter);
			 
			 report.setSystemInfo("OS", "Windows-64Bit");
			 report.setSystemInfo("OS-Version", "10.0.0");
			 report.setSystemInfo("Browser", "Chrome");
			 report.setSystemInfo("Browser-Version", "88.0.0.1");
			 
			 htmlreporter.config().setDocumentTitle("HotelAndCruiseDetails_TEAM#5");
			 htmlreporter.config().setReportName(documentTitle);
			 htmlreporter.config().setTestViewChartLocation(ChartLocation.TOP);
			 htmlreporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
			 
		 }
		 
		 return report;
	 }
	
}
