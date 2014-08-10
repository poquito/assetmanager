
package at.poquito.assetmanager;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Mario Rodler
 */
public interface AssetTask {

	void addAttachment(String name, File attachment);

	void addAttachment(String name, InputStream attachment);

	void execute();

	<R> R execute(Class<R> resultType);

	void executeAsynchronous();

	void setCorrelationId(String correlationId);

	void setDestination(AssetPath destination);

	void setProperties(Properties properties);

	void setSource(AssetPath source);
}
