package Config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by rik on 28-3-17.
 */
public class config {
	public static Properties prop;

	//http://www.mkyong.com/java/java-properties-file-examples/

	static {
		prop = new Properties();
		InputStream input = null;
System.out.print("ok");
		try {

			input = new FileInputStream("config.properties");

			// load a properties file
			prop.load(input);
System.out.print("good");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String get(String property){
		return prop.getProperty(property);
	}
}
