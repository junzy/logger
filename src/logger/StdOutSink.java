package logger;

public class StdOutSink implements LogSink{

	@Override
	public String getLogSinkType() {
		return "stdout";
	}

	// this logs to stdout
	@Override
	public void writeToLog(String log, LogLevelEnum logLevel, String className) {
		System.out.println(className + " : " + logLevel.toString() + " : " + log);
	}
	
	

}
