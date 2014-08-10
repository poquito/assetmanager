
package at.poquito.assetmanager.event;

import at.poquito.assetmanager.AssetPath;

/**
 * @author mario rodler
 * 
 */
public class AssetRemoved extends AbstractAssetEvent {

	public AssetRemoved(AssetPath path) {
		super(path);
	}
}
