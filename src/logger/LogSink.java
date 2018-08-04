package logger;

public interface LogSink {
	public String getLogSinkType();
	public void writeToLog(String log, LogLevelEnum logLevel, String className);
}
