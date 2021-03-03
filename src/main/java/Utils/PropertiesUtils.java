package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesUtils {

	private static Properties props;

	public static Properties getProperties(String filename) throws Exception {

		props = new Properties();

		try (FileInputStream out = new FileInputStream(
				new File(System.getProperty("user.dir"), "src\\test\\resources\\ObjectRepository\\" + filename));) {
			props.load(out);

			return props;
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
	}
}
