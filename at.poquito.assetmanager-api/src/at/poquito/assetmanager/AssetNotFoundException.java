
package at.poquito.assetmanager;


public class AssetNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AssetNotFoundException(AssetPath path) {
		super(path.getPath());
	}

	public AssetNotFoundException(String path) {
		super(path);
	}

}
