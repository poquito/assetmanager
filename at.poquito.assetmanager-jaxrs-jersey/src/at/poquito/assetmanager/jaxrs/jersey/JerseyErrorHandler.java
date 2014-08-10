package at.poquito.assetmanager.jaxrs.jersey;

import java.text.MessageFormat;

import javax.ws.rs.core.Response.Status;

import at.poquito.assetmanager.AssetManagerException;
import at.poquito.assetmanager.AssetNotFoundException;
import at.poquito.assetmanager.NotAuthorizedException;
import at.poquito.assetmanager.util.IFunction;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

/**
 * the default implementation of the {@link ClientErrorHandler} interface.
 * 
 * this class is used as error handler by the {@link AbstractJerseyClient} to
 * map Response Objects to application exceptions.
 * 
 * it is safe to overwrite this class and adapt it to your needs.
 * 
 * @author Mario Rodler
 */
public class JerseyErrorHandler implements ClientErrorHandler {

	/**
	 * this method is the entry point for the {@link AbstractJerseyClient} to
	 * handle any errors.
	 */
	@Override
	public void onError(IFunction<?, ?> function, WebResource resource, Exception exception) {
		if (exception instanceof UniformInterfaceException) {
			ClientResponse response = ((UniformInterfaceException) exception).getResponse();
			mapStatusToException(function, resource.getURI().getPath(), response);
		}
	}

	private RuntimeException mapStatusToException(IFunction<?, ?> function, String path, ClientResponse response) {
		String name = function.getClass().getSimpleName();
		Status status = Status.fromStatusCode(response.getStatus());
		if (status == null) {
			onUnexpectedError(name, path, response.getStatus());
		} else {
			switch (status) {
			case NOT_FOUND:
				onNotFound(name, path);
			case FORBIDDEN:
				onUnauthorized(name, path);
			case UNAUTHORIZED:
				onUnauthorized(name, path);
			default:
				onUnexpectedError(name, path, response.getStatus());
			}
		}
		return null;
	}

	/**
	 * overwrite this method to handle a HTTP NOT FOUND reply.
	 * 
	 * @param function
	 *            the name of the function which failed.
	 * @param path
	 *            the path of the missing resource.
	 */
	public void onNotFound(String function, String path) {
		throw new AssetNotFoundException(path);
	}

	/**
	 * overwrite this method to handle a HTTP FORBIDDEN reply.
	 * 
	 * @param function
	 *            the name of the function which failed.
	 * @param path
	 *            the path of the resource.
	 */
	public void onForbidden(String function, String path) {
		throw new NotAuthorizedException(MessageFormat.format("{0}: forbidden to access {1}", function, path));
	}

	/**
	 * overwrite this method to handle a HTTP UNAUTHORIZED reply.
	 * 
	 * @param function
	 *            the name of the function which failed.
	 * @param path
	 *            the path of the resource.
	 */
	public void onUnauthorized(String function, String path) {
		throw new NotAuthorizedException(MessageFormat.format("{0}: not authorized to access {1}", function, path));
	}

	/**
	 * overwrite this method to handle any other HTTP replies.
	 * 
	 * @param function
	 *            the name of the function which failed.
	 * @param path
	 *            the path of the resource.
	 * @param status
	 *            the HTTP status code.
	 */
	public void onUnexpectedError(String function, String path, int status) {
		throw new AssetManagerException(MessageFormat.format("{0}: failed for {1} with status {2}", function, path, status));
	}

}
