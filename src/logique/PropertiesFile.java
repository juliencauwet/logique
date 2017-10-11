package logique;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFile {
	
	public static String getPropertiesFile(String att) {
		
		Properties prop = new Properties();
		
		try {
			InputStream input = new FileInputStream("src/resources/config.properties");
			prop.load(input);			
		}catch (Exception e) {
			Main.nbDigits = 5;
			Main.chances = 12;
			Main.dev = false;
		}
		
		return prop.getProperty(att);
	}

}
