package at.poquito.assetmanager.jaxrs.jersey;

import at.poquito.assetmanager.AssetRepository;
import at.poquito.assetmanager.AssetRepositoryIndex;

import com.sun.jersey.api.client.WebResource;

public class JerseyAdminClient extends JerseyFunctionHandler {

	private WebResource adminApi;

	public JerseyAdminClient(WebResource adminApi) {
		this.adminApi = adminApi;
	}

	public AssetRepositoryIndex getRepositoryIndex() {
		WebResource resource = adminApi.path("/repositories/");
		return apply(new GetAssetRepositoryIndex(), resource);
	}

	public void storeRepository(AssetRepository repository) {
		WebResource resource = adminApi.path("/repositories/");
		apply(new PutAssetRepositoryIndex(repository), resource);
	}

	public void removeRepository(String id) {
		WebResource resource = adminApi.path("/repositories/" + id);
		apply(new RemoveAssetRepository(), resource);
	}

}
