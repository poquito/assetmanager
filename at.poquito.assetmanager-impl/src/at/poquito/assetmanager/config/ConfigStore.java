package at.poquito.assetmanager.config;

import java.net.URL;

import at.poquito.assetmanager.AssetManagerException;
import at.poquito.assetmanager.intern.AssetManagerFactory;

public class ConfigStore {

	public AssetManagerConfiguration load() {
		return AssetManagerConfiguration.readConfiguration(getConfigurationURL());
	}

	public boolean isNewVersionAvailable() {
		return false;
	}

	private URL getConfigurationURL() {
		return AssetManagerFactory.class.getResource("assetmanager.xml");
	}

	public void safe(AssetManagerConfiguration configuration) {
		throw new AssetManagerException("current configuration is readonly");
	}

}
