package at.poquito.assetmanager.jaxrs.jersey;

import at.poquito.assetmanager.AssetIndex;
import at.poquito.assetmanager.util.IFunction;

import com.sun.jersey.api.client.WebResource;

class GetIndex implements IFunction<WebResource, AssetIndex> {

	@Override
	public AssetIndex apply(WebResource resource) {
		return resource.get(AssetIndex.class);
	}
}