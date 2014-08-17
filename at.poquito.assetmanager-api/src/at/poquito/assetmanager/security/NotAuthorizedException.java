package at.poquito.assetmanager.security;

import at.poquito.assetmanager.AssetManagerException;


public class NotAuthorizedException extends AssetManagerException {
	private static final long serialVersionUID = 1L;

	public NotAuthorizedException(String message) {
		super(message);
	}

}
