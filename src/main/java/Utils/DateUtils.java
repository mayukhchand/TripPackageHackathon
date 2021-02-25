package Utils;

import java.util.Date;

public class DateUtils {
	public static String getDateString() {
		Date date = new Date();
		//System.out.println(date.toString().replaceAll(" ", "_").replaceAll(":", "_"));
		return date.toString().replaceAll(" ", "_").replaceAll(":", "_");
	}
}
