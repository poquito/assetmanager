package at.poquito.assetmanager.security;


public interface AuthorizationServiceFactory<T> {

	AuthorizationService create(T request);

}
