package Utils;

import java.util.Date;

public class DateUtils {
	public static String getDateString() {
		Date date = new Date();
		return date.toString().replaceAll(" ", "_").replaceAll(":", "_");
	}
}
