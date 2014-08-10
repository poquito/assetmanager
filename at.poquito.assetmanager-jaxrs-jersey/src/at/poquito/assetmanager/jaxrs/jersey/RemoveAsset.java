package at.poquito.assetmanager.jaxrs.jersey;

import at.poquito.assetmanager.util.IFunction;

import com.sun.jersey.api.client.WebResource;

class RemoveAsset implements IFunction<WebResource, Object> {

	@Override
	public Object apply(WebResource resource) {
		resource.delete();
		return null;
	}
}