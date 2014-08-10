
package at.poquito.assetmanager.event;

import at.poquito.assetmanager.AssetPath;

/**
 * @author mario rodler
 * 
 */
public abstract class AbstractAssetEvent {
	private AssetPath path;

	/**
	 * @param path
	 */
	public AbstractAssetEvent(AssetPath path) {
		this.path = path;
	}

	/**
	 * @return the path
	 */
	public AssetPath getPath() {
		return path;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [path=" + path + "]";
	}

}
