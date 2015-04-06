package at.poquito.assetmanager.rest;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import at.poquito.assetmanager.AssetRepository;
import at.poquito.assetmanager.AssetRepositoryIndex;
import at.poquito.assetmanager.config.AssetManagerConfiguration;
import at.poquito.assetmanager.intern.AssetManagerApplication;

public class RestfulRepositoryAdmin {

	@Inject
	private AssetManagerApplication app;

	@PUT
	@Path("repositories")
	public void addRepository(AssetRepository repository) {
		assertConfigIsEditable();
		AssetManagerConfiguration configuration = app.loadConfiguration();
		configuration.storeRepositoryConfiguration(repository);
		app.storeConfiguration(configuration);
	}

	@GET
	@Path("repositories")
	public AssetRepositoryIndex getRepositoryIndex() {
		AssetManagerConfiguration configuration = app.loadConfiguration();
		AssetRepositoryIndex index = new AssetRepositoryIndex(configuration.getRepositories());
		index.setReadonly(!app.isConfigEditable());
		return index;
	}

	@DELETE
	@Path("repositories/{id}")
	public void storeRepository(@PathParam("id") String id) {
		assertConfigIsEditable();
		AssetManagerConfiguration configuration = app.loadConfiguration();
		AssetRepository repository = configuration.findRepository(id);
		if (repository != null) {
			configuration.removeRepositoryConfiguration(repository);
		}
		app.storeConfiguration(configuration);
	}

	private void assertConfigIsEditable() {
		if (!app.isConfigEditable()) {
			throw new WebApplicationException(Status.FORBIDDEN);
		}
	}
}
