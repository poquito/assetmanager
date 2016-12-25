
package at.poquito.assetmanager;

import java.util.Properties;

/**
 * @author Mario Rodler
 */
public interface AssetTask {

	void execute();

	<R> R execute(Class<R> resultType);

	void executeAsynchronous();

	void setCorrelationId(String correlationId);

	void setDestination(AssetPath destination);

	void setProperties(Properties properties);

	void setSource(AssetPath source);
}
