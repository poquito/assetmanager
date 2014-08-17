package at.poquito.assetmanager.security;

/**
 * implementors of this interface need to provide the userId of the current user
 * and check if the user has a given permission.
 * 
 */
public interface AuthorizationService {

	String currentUser();

	boolean hasPermission(String permission);

}
