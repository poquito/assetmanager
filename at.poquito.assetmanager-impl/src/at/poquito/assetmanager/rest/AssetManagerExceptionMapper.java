package at.poquito.assetmanager.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import at.poquito.assetmanager.AssetManagerException;

@Provider
public class AssetManagerExceptionMapper implements ExceptionMapper<AssetManagerException> {

	@Override
	public Response toResponse(AssetManagerException exception) {
		
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
	}

}
