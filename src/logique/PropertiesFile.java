package logique;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class PropertiesFile {
	
	private static final Logger logger = Logger.getLogger(Main.class);

	public static String getPropertiesFile(String att) {

		Properties prop = new Properties();

		try {
			InputStream input = new FileInputStream("src/resources/config.properties");
			prop.load(input);
		} catch (Exception e) {
			logger.log(Level.ERROR, "Certaines valeurs de variable n'ont pas pu être récupérées dans le fichier config.properties. Elles ont donc été attribuées par défaut.");
			
			Main.nbDigits = 5;
			Main.chances = 12;
			Main.dev = false;
		}

		return prop.getProperty(att);
	}

}
