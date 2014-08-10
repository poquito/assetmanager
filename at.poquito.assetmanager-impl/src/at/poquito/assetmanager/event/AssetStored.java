
package at.poquito.assetmanager.event;

import at.poquito.assetmanager.AssetPath;

/**
 * @author mario rodler
 * 
 */
public class AssetStored extends AbstractAssetEvent {

	/**
	 * @param path
	 */
	public AssetStored(AssetPath path) {
		super(path);
	}
}
