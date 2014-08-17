package at.poquito.assetmanager.intern;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import at.poquito.assetmanager.AssetManager;
import at.poquito.assetmanager.AssetManagerContext;
import at.poquito.assetmanager.intern.AssetManagerFactory;

@ApplicationScoped
class AssetManagerApplication {

	@Inject
	private AssetManagerFactory assetManagerFactory;

	@Inject
	private AssetManagerContext assetManagerContext;

	@Produces
	public AssetManager createAssetManager() {
		return assetManagerFactory.createAssetManager(assetManagerContext);
	}

}
