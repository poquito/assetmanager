package at.poquito.assetmanager.jaxrs.jersey;

import at.poquito.assetmanager.util.IFunction;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

class AssetExists implements IFunction<WebResource, Boolean> {

	@Override
	public Boolean apply(WebResource resource) {
		ClientResponse response = resource.head();
		if (response.getStatus() == 200) {
			return Boolean.TRUE;
		}
		if (response.getStatus() == 404) {
			return Boolean.FALSE;
		}
		throw new UniformInterfaceException(response);
	}
}