package client.utils;

public class NumberUtils {
	public static Float parseFloatOrNull(String string) {
		try {
			return Float.parseFloat(string);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
