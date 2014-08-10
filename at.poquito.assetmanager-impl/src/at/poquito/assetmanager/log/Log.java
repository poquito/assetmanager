package at.poquito.assetmanager.log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {

	private final Logger log;
	private Level debugLevel;

	public Log(Logger log, Level debugLevel) {
		this.log = log;
		this.debugLevel = debugLevel;
	}

	public void debug(String message) {
		log.log(debugLevel, message);
	}

	public void debug(String message, Object... args) {
		if (log.isLoggable(debugLevel)) {
			log.log(debugLevel, String.format(message, args));
		}
	}

	public void info(String message) {
		log.log(Level.INFO, message);
	}

	public void info(String message, Object... args) {
		if (log.isLoggable(Level.INFO)) {
			log.log(Level.INFO, String.format(message, args));
		}
	}

}
