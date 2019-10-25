package custome_log4j;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter {
	@Override
	public String format(LogRecord record) {
		return record.getThreadID() + "::" + record.getSourceClassName() + "::" + record.getSourceMethodName() + "::"
				+ new Date(record.getMillis()) + "::" + record.getMessage() + "\n";
	}

}
