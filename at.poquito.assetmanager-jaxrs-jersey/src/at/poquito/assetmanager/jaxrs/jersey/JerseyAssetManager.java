package at.poquito.assetmanager.jaxrs.jersey;

import at.poquito.assetmanager.AssetPath;

import com.sun.jersey.api.client.WebResource;

/**
 * This is a concrete implementation of the {@link AbstractJerseyClient} class
 * which uses the AssetPath class as path references.
 * 
 * @author Mario Rodler
 * 
 */
public class JerseyAssetManager extends AbstractJerseyClient<AssetPath> {

	private WebResource repository;

	/**
	 * creates a new instance which will use the given WebResource as prefix for
	 * all jersey connections.
	 * 
	 * for example: http://remote-host/assetmanager/api/repository
	 * 
	 * @param repository
	 *            the complete path to the repository resource.
	 * 
	 */
	public JerseyAssetManager(WebResource repository) {
		this.repository = repository;
	}

	/**
	 * converts path references to WebResource instances.
	 * 
	 * @param path
	 *            the path to be converted.
	 * @return a WebResource representing the path to the resource.
	 */
	@Override
	public WebResource createResource(AssetPath path) {
		return repository.path(path.getPath());
	}

}
