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
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop.getProperty(att);
	}

}
