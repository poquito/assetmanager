package at.poquito.assetmanager;


public class NotAuthorizedException extends AssetManagerException {
	private static final long serialVersionUID = 1L;

	public NotAuthorizedException(String message) {
		super(message);
	}

}
