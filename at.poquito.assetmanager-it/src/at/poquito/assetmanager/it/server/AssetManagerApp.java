package at.poquito.assetmanager.it.server;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import at.poquito.assetmanager.config.AssetManagerConfiguration;

@ApplicationScoped
public class AssetManagerApp {

	@Context
	private ServletContext servletContext;


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
