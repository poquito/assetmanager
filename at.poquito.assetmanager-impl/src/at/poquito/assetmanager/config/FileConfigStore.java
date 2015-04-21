package at.poquito.assetmanager.config;

import java.io.File;

import at.poquito.assetmanager.AssetManagerException;

public class FileConfigStore extends ConfigStore {

	private static final int CACHE_TIME_MS = 5000;

	private File configFile;

	private long lastModified;

	private long cacheUntil;

	public FileConfigStore(File configFile) {
		this.configFile = configFile;
	}

	@Override
	public AssetManagerConfiguration load() {
		if (configFile.exists()) {
			lastModified = configFile.lastModified();
			cacheUntil = System.currentTimeMillis() + CACHE_TIME_MS;
			return AssetManagerConfiguration.readConfiguration(configFile);
		}
		throw new AssetManagerException("assetmanager configuration file not found:" + configFile.getAbsolutePath());
	}

	@Override
	public void safe(AssetManagerConfiguration configuration) {
		configuration.writeTo(configFile);
	}

	@Override
	public boolean isNewVersionAvailable() {
		if (cacheUntil > System.currentTimeMillis()) {
			long current = configFile.lastModified();
			if (lastModified != current) {
				lastModified = current;
				return true;
			}
		}
		return false;
	}
}
