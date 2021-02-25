package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class LoadProperties {

	/**
	 * Writes the test data to driver.properties file in ObjectRepository folder
	 */
	public static void writeProperties() {
		
		Properties props = new Properties();
		
		try(FileOutputStream out = new FileOutputStream(new File( System.getProperty("user.dir"),"src\\test\\resources\\ObjectRepository\\driver.properties"));){
			
			props.setProperty("browsername", "chrome");
			//props.setProperty("browsername", "edge");
			//props.setProperty("browsername", "firefox");
			
			props.setProperty("baseurl", "https://www.tripadvisor.in/");
			
			
			props.store(out, "Web Drivers Properties");
			System.out.println("Successfully Created Properties File.");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		writeProperties();
	}
}
