
package at.poquito.assetmanager.processing;

import java.util.Properties;

import at.poquito.assetmanager.Asset;

/**
 * @author Mario Rodler
 */
public interface TaskContext {

	String getCorrelationId();

	Asset getDestinationAsset();

	Properties getProperties();

	Asset getSourceAsset();

	String getTaskId();

	String getTaskName();

}
