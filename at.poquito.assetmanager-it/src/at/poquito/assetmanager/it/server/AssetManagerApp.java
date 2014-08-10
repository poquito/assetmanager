package at.poquito.assetmanager.it.server;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import at.poquito.assetmanager.AssetManager;
import at.poquito.assetmanager.AssetManagerContext;
import at.poquito.assetmanager.config.AssetManagerConfiguration;
import at.poquito.assetmanager.intern.AssetManagerFactory;

@ApplicationScoped
public class AssetManagerApp {

	@Inject
	private AssetManagerFactory assetManagerFactory;

	@Inject
	private AssetManagerContext assetManagerContext;

	@Context
	private ServletContext servletContext;


	@Produces
	public AssetManager createAssetManager() {
		return assetManagerFactory.createAssetManager(assetManagerContext);
	}

	URL getAssetManagerConfigurationURL() {
		try {
			return servletContext.getResource("/WEB-INF/assetmanager.xml");
		} catch (MalformedURLException e) {
			throw new RuntimeException("assetmanager.xml not found");
		}
	}

	File getWorkDir() {
		String userHome = System.getProperty("user.home");
		File userHomeDir = new File(userHome);
		return new File(userHomeDir, "MBoxMediaserverClient");
	}
	
	@Produces
	public AssetManagerConfiguration getAssetManagerConfiguration(){
		return AssetManagerConfiguration.readConfiguration(getAssetManagerConfigurationURL());
	}

}
