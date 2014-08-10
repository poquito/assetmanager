
package at.poquito.assetmanager.processing;

import java.util.List;
import java.util.Properties;

import at.poquito.assetmanager.Asset;
import at.poquito.assetmanager.AssetStream;

/**
 * @author Mario Rodler
 */
public interface TaskContext {

	List<AssetStream> getAttachments();

	String getCorrelationId();

	Asset getDestinationAsset();

	Properties getProperties();

	Asset getSourceAsset();

	String getTaskId();

	String getTaskName();

}
