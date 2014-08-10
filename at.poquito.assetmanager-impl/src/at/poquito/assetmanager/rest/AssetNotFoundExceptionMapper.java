package at.poquito.assetmanager.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import at.poquito.assetmanager.AssetNotFoundException;

@Provider
public class AssetNotFoundExceptionMapper implements ExceptionMapper<AssetNotFoundException> {

	@Override
	public Response toResponse(AssetNotFoundException exception) {
		return Response.status(Status.NOT_FOUND).build();
	}

}
