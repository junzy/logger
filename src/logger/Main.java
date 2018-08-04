package logger;

public class Main {

	public void main(String[] args) {
		Logger log = new Logger(this.getClass());
		log.debug("LOGGING IT IN DEBUG MODE");
		log.error("LOGGING IT IN ERROR MODE");
		log.info("LOGGING IT IN ERROR MODE");
	}
}
