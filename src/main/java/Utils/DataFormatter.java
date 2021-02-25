package Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFormatter {

	/*
	 * Extracts the Package name and price Formats the data and returns the data
	 */
	public static String formatter(String data) {

		Pattern p = Pattern.compile(" [0-9]+,[0-9]+");
		Matcher m = p.matcher(data);

		String formatted = "";

		while (m.find()) {
			formatted += m.group().trim();
		}

		return formatted;
	}

}
