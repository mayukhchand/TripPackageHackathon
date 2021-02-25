package Utils;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;

public class ExtentReportsManager {
	  
	 static ExtentReports report;
	 
	 public static ExtentReports getInstance() {
		 String filename = DateUtils.getDateString()+".html";
		 
		 if( report ==null) {
			 ExtentHtmlReporter htmlreporter = new ExtentHtmlReporter(
					 new File(System.getProperty("user.dir"),"test-output//"+filename));
			 
			 report = new ExtentReports();
			 
			 report.attachReporter(htmlreporter);
			 
			 report.setSystemInfo("OS", "Windows-64Bit");
			 report.setSystemInfo("OS-Version", "10.0.0");
			 report.setSystemInfo("Browser", "Chrome");
			 report.setSystemInfo("Browser-Version", "88.0.0.1");
			 
			 htmlreporter.config().setDocumentTitle("Postman Login-Invalid");
			 htmlreporter.config().setReportName("All feilds invalid in user login");
			 htmlreporter.config().setTestViewChartLocation(ChartLocation.TOP);
			 htmlreporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
			 
		 }
		 
		 return report;
	 }
	
}
