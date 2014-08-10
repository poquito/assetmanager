package at.poquito.assetmanager.jaxrs.jersey;

import at.poquito.assetmanager.util.IFunction;

import com.sun.jersey.api.client.WebResource;

public interface ClientErrorHandler {

	public abstract void onError(IFunction<?, ?> function, WebResource resource, Exception exception);

}