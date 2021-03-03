package Utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;

public class TakeScreenShot {
	public static void takeScreenshot(WebDriver driver, ExtentTest logger, String message){
		TakesScreenshot screenshot = (TakesScreenshot)driver; 
		System.out.println(driver);
		File scr = screenshot.getScreenshotAs(OutputType.FILE); 
		File dest = new File(System.getProperty("user.dir"),"Screenshots//"+DateUtils.getDateString()+".png");
		
		try {
			FileUtils.copyFile(scr, dest);
			logger.addScreenCaptureFromPath(dest.toString(),message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
