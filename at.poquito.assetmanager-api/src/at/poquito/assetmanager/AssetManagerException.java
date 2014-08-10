package at.poquito.assetmanager;

public class AssetManagerException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AssetManagerException(String message) {
		super(message);
	}

	public AssetManagerException(String message, Throwable e) {
		super(message, e);
	}

}
