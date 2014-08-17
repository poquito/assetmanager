package at.poquito.assetmanager.intern;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import at.poquito.assetmanager.AssetManagerContext;
import at.poquito.assetmanager.security.AuthorizationService;

@SessionScoped
class DefaultAssetManagerContext implements AssetManagerContext, Serializable {
	private static final long serialVersionUID = 1L;
	private AuthorizationService authorizationService;

	public DefaultAssetManagerContext(AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	@Override
	public String username() {
		return authorizationService.currentUser();
	}

	@Override
	public boolean hasPermission(String permission) {
		return authorizationService.hasPermission(permission);
	}

}
