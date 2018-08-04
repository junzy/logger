package logger;

import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Set;

public class Logger {

	private LogLevelEnum logLevel;
	private LogSink logSink;
	private String className;
	private static final String FILENAME = "log_config.json";

	public void setLogLevel(LogLevelEnum logLev) {
		this.logLevel = logLev;
	}

	Logger(Class className) {
		this.className = className.getName();
		setLogSink();
	}

	/*
	Reads the config file and gets the defined sink name. At runtime checks if a matching sinkname exists
	If so it sets the loggers sink to the one define in the config file
	*/
	private void setLogSink() {
		String sinkName = null;
		// Read config file
		try (FileReader fr = new FileReader(FILENAME)) {
			BufferedReader br = new BufferedReader(fr);
			JSONParser parser = new JSONParser();
			Object obj = (JSONObject) parser.parse(new FileReader(FILENAME));

			sinkName = (String) obj.get("sinkName");
		}

		// Get the log Sink class at runtime from the config file
		// this allows us to drop in new sinkLog files that dont require they library to
		// be rebuilt again.
		Reflections reflections = new Reflections("logger");
		Set<Class<? extends LogSink>> subTypes = reflections.getSubTypesOf(LogSink.class);
		Iterator<Class<? extends LogSink>> aggItr = subTypes.iterator();

		while (aggItr.hasNext()) {
			Class<? extends LogSink> aggClassName = aggItr.next();
			LogSink sink;
			try {
				sink = aggClassName.newInstance();
				String format = sink.getLogSinkType();
				if (format.equals(sinkName)) {
					this.logSink = sink;
				}
			} catch (InstantiationException | IllegalAccessException e) {
				System.out.println("Error while instantiating sink instance");
			}
		}
	}

	public void debug(String log) {
		setLogLevel(LogLevelEnum.DEBUG);
		logSink.writeToLog(log, logLevel, className);
	}

	public void error(String log) {
		setLogLevel(LogLevelEnum.ERROR);
		logSink.writeToLog(log, logLevel, className);
	}

	public void info(String log) {
		setLogLevel(LogLevelEnum.INFO);
		logSink.writeToLog(log, logLevel, className);
	}

}
