package at.poquito.assetmanager.intern;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import at.poquito.assetmanager.AssetManagerContext;
import at.poquito.assetmanager.security.AuthorizationService;
import at.poquito.assetmanager.security.AuthorizationServiceFactory;

@RequestScoped
public class AssetManagerContextFactory {

	private static final String UNAUTHENTICATED = "UNAUTHENTICATED";

	private HttpServletRequest currentRequest;

	@Inject
	private Instance<AuthorizationServiceFactory<HttpServletRequest>> authorizers;

	@Produces
	public AssetManagerContext create() {
		return new DefaultAssetManagerContext(getAuthorizationService());
	}

	private AuthorizationService getAuthorizationService() {
		if (authorizers.isUnsatisfied()) {
			return new AuthorizationService() {

				@Override
				public boolean hasPermission(String permission) {
					return false;
				}

				@Override
				public String currentUser() {
					return UNAUTHENTICATED;
				}
			};
		}
		return authorizers.get().create(currentRequest);
	}

	public void setCurrentRequest(HttpServletRequest currentRequest) {
		this.currentRequest = currentRequest;
	}

}
