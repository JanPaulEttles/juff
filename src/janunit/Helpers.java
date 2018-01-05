package janunit;

public class Helpers {
	
	public static String replaceToken(String string, String find, String replace) {
		return string.replace("{" + find + "}", replace);
	}
}
