
package at.poquito.assetmanager.event;

import at.poquito.assetmanager.AssetPath;

/**
 * @author mario rodler
 * 
 */
public class AssetRetrieved extends AbstractAssetEvent {

	/**
	 * @param path
	 */
	public AssetRetrieved(AssetPath path) {
		super(path);
	}
}
