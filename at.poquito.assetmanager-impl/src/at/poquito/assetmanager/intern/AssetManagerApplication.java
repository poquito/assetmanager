package at.poquito.assetmanager.intern;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import at.poquito.assetmanager.AssetManager;
import at.poquito.assetmanager.AssetManagerContext;
import at.poquito.assetmanager.config.AssetManagerConfiguration;
import at.poquito.assetmanager.config.ConfigStore;
import at.poquito.assetmanager.config.FileConfigStore;
import at.poquito.assetmanager.security.NotAuthorizedException;
import at.poquito.assetmanager.security.Permissions;

@ApplicationScoped
public class AssetManagerApplication {

	@Inject
	private AssetManagerContext assetManagerContext;

	private AssetManagerFactory assetManagerFactory;

	private ConfigStore configStore;

	private AssetManagerConfiguration configuration;

	private Permissions adminPermissions;

	public AssetManagerApplication() {
		configStore = createConfigStore();
	}

	@Produces
	public AssetManager createAssetManager() {
		if (mustLoadConfiguration()) {
			resetConfiguration();
			assetManagerFactory = new AssetManagerFactory(configuration);
		}
		return assetManagerFactory.createAssetManager(assetManagerContext);
	}

	private void resetConfiguration() {
		configuration = configStore.load();
		resetAdminPermissions();
	}

	private void resetAdminPermissions() {
		adminPermissions = configuration.buildPermissions(configuration.getAdminPermission());
	}

	public boolean isConfigEditable() {
		return adminPermissions.isAuthorized(assetManagerContext);
	}

	public AssetManagerConfiguration loadConfiguration() {
		return configStore.load();
	}

	private ConfigStore createConfigStore() {
		String property = System.getProperty("assetmanager.configURL");
		if (property == null) {
			return new ConfigStore();
		}
		return new FileConfigStore(URI.create(property));
	}

	private boolean mustLoadConfiguration() {
		return configuration == null || configStore.isNewVersionAvailable();
	}

	public void storeConfiguration(AssetManagerConfiguration configuration) {
		if (!isConfigEditable()) {
			String message = assetManagerContext.username() + " is not authorized to store configuration";
			throw new NotAuthorizedException(message);
		}
		configStore.safe(configuration);
	}

}
