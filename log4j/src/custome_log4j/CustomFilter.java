package custome_log4j;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class CustomFilter implements Filter {
	@Override
	public boolean isLoggable(LogRecord log) {
		// don't log CONFIG logs in file
		if (log.getLevel() == Level.CONFIG)
			return false;
		return true;
	}
}
