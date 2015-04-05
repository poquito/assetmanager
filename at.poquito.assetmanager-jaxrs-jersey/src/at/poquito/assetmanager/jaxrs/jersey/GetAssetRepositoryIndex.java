package at.poquito.assetmanager.jaxrs.jersey;

import at.poquito.assetmanager.AssetRepositoryIndex;
import at.poquito.assetmanager.util.IFunction;

import com.sun.jersey.api.client.WebResource;

class GetAssetRepositoryIndex implements IFunction<WebResource, AssetRepositoryIndex> {

	@Override
	public AssetRepositoryIndex apply(WebResource resource) {
		return resource.get(AssetRepositoryIndex.class);
	}
}