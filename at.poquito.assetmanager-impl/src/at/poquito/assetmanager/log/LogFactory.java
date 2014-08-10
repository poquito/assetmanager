package at.poquito.assetmanager.log;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class LogFactory {
	private static Level debugLevel = Level.INFO;

	public static Log create(Class<?> loggingClass) {
		return new Log(Logger.getLogger(loggingClass.getName()), debugLevel);
	}

}
