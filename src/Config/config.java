package Config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by rik on 28-3-17.
 */
public class config {
	private static HashMap<String, Properties> configs = new HashMap<>();

	private static void addProperties(String file){
		Properties prop = new Properties();
		InputStream input = null;
		System.out.println("ok");
		try {

			input = new FileInputStream("Config/" + file + ".properties");

			// load a properties file
			prop.load(input);
			System.out.println("ok");
		} catch (IOException ex) {
//			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
//					e.printStackTrace();
				}
			}
			configs.put(file, prop);

		}
	}

	public static Properties getConfig(String file) {System.out.println("ok");
		if (configs.containsKey(file)) {
			addProperties(file);
		}
		return configs.get(file);
	}

//	public static String get(String property){
//		return prop.getProperty(property);
//	}
}
