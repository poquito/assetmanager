package at.poquito.assetmanager.jaxrs.jersey;

import com.sun.jersey.api.client.WebResource;

import at.poquito.assetmanager.AssetPath;
import at.poquito.assetmanager.util.IFunction;

class CopyAsset implements IFunction<WebResource, Object> {
	public static final String X_ASSETMANAGER_COPY_DESTINATION = "x-assetmanager-copy-destination";
	private final AssetPath destination;

	public CopyAsset(AssetPath destination) {
		this.destination = destination;
	}

	@Override
	public Object apply(WebResource resource) {
		resource.header(X_ASSETMANAGER_COPY_DESTINATION, destination.getPath()).put();
		return null;
	}
}