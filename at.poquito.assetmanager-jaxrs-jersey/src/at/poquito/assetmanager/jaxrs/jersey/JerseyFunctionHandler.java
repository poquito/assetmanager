package at.poquito.assetmanager.jaxrs.jersey;

import at.poquito.assetmanager.util.IFunction;

import com.sun.jersey.api.client.WebResource;

public class JerseyFunctionHandler {

	private static ClientErrorHandler DEFAULT_ERROR_HANDLER = new JerseyErrorHandler();

	private ClientErrorHandler errorHandler;

	public JerseyFunctionHandler() {
		errorHandler = DEFAULT_ERROR_HANDLER;
	}

	public void setErrorHandler(ClientErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	protected <A> A apply(IFunction<WebResource, A> function, WebResource resource) {
		try {
			return function.apply(resource);
		} catch (Exception e) {
			onError(function, resource, e);
			throw new RuntimeException("execution failed, error not handled", e);
		}
	}

	protected void onError(IFunction<?, ?> function, WebResource resource, Exception exception) {
		errorHandler.onError(function, resource, exception);
	}

}
