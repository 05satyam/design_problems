/**
 * this is a main class where app starts.

 * Logger instance will be created and associated with the Root LoggerConfig. Root LoggerConfig will be used when there is no configuration file or when you’re obtaining a logger with name not defined in the logger declarations.
 * */

package custome_log4j;

import java.io.FileInputStream;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class App {

	static Logger logger = Logger.getLogger(App.class.getName());

	public static void main(String[] args) {
		try {
			//default
			LogManager.getLogManager().readConfiguration(new FileInputStream("mylogging.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.setLevel(Level.FINE);
		logger.addHandler(new CustomHandler());
		try {
			//custom- high preference 
			String path = "C:/Users/satyam/Desktop/u.log";
			Handler fileHandler = new FileHandler(path, 2000, 1);
			fileHandler.setFormatter(new CustomFormatter());
			fileHandler.setFilter(new CustomFilter());
			logger.addHandler(fileHandler);
			for (int i = 0; i < 1000; i++) {
				logger.log(Level.INFO, "Msg " + i);
			}
			logger.log(Level.CONFIG, "Config data");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
