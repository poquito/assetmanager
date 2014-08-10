package at.poquito.assetmanager.jaxrs.jersey;

import java.io.InputStream;

import javax.ws.rs.core.MediaType;

import at.poquito.assetmanager.util.IFunction;

import com.sun.jersey.api.client.WebResource;

class StoreAsset implements IFunction<WebResource, Object> {
	private final InputStream inputStream;

	public StoreAsset(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public Object apply(WebResource resource) {
		resource.type(MediaType.APPLICATION_OCTET_STREAM).put(inputStream);
		return null;
	}
}