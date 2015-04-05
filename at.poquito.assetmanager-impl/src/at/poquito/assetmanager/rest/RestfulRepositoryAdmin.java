package at.poquito.assetmanager.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import at.poquito.assetmanager.AssetRepositoryIndex;
import at.poquito.assetmanager.config.AssetManagerConfiguration;


public class RestfulRepositoryAdmin {
	
	@Inject
	private AssetManagerConfiguration configuration;

	@GET
	@Path("repositories")
	public AssetRepositoryIndex getRepositoryIndex(){
		return new AssetRepositoryIndex(configuration.getRepositories());
	}
}
