package at.poquito.assetmanager.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import at.poquito.assetmanager.Asset;
import at.poquito.assetmanager.AssetIndex;
import at.poquito.assetmanager.AssetManager;
import at.poquito.assetmanager.AssetNotFoundException;
import at.poquito.assetmanager.AssetPath;
import at.poquito.assetmanager.event.AssetRemoved;
import at.poquito.assetmanager.event.AssetRetrieved;
import at.poquito.assetmanager.event.AssetStored;
import at.poquito.assetmanager.util.CopyStream;
import at.poquito.assetmanager.util.IOUtils;

public class RestfulAssetManager {

	@Inject
	private AssetManager assetManager;

	@Inject
	private BeanManager beanManager;

	@Context
	private HttpServletRequest request;

	@GET
	@Path("{path:.*}")
	public Response getPath(@PathParam("path") String path) throws Throwable {
		return handleGet(path);
	}

	@HEAD
	@Path("{path:.*}")
	public Response getInfo(@PathParam("path") String path) throws Throwable {
		final AssetPath assetPath = new AssetPath(path);
		Asset asset = assetManager.findAsset(assetPath);
		if (asset == null) {
			throw new AssetNotFoundException(path);
		}
		File file = asset.getFile();
		if (file.isDirectory()) {
			return Response.ok().build();
		}
		return Response.ok().header("Content-Length", Long.toString(file.length())).build();
	}

	@DELETE
	@Path("{path:.*}")
	public void remove(@PathParam("path") String path) {
		AssetPath assetPath = new AssetPath(path);
		assetManager.remove(assetPath);
		beanManager.fireEvent(new AssetRemoved(assetPath));
	}

	@PUT
	@Path("{path:.*}")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	public void store(@PathParam("path") String path, InputStream inputStream) {
		AssetPath assetPath = new AssetPath(path);
		assetManager.store(assetPath, inputStream);
		beanManager.fireEvent(new AssetStored(assetPath));
	}

	@GET
	public Response getRoot() {
		return handleGet("/");
	}

	public Response handleGet(String path) {
		final AssetPath assetPath = new AssetPath(path);
		if (assetPath.isDirectory()) {
			return getIndex(assetPath);
		}
		return retrieve(assetPath);
	}

	public Response retrieve(final AssetPath assetPath) {
		Asset asset = assetManager.getAsset(assetPath);
		final File file = asset.getFile();
		if (file.isDirectory()) {
			URI uri = URI.create(request.getRequestURL() + "/");
			return Response.status(Status.SEE_OTHER).location(uri).build();
		}

		StreamingOutput output = new StreamingOutput() {

			@Override
			public void write(OutputStream outputStream) throws IOException, WebApplicationException {
				InputStream inputStream = IOUtils.createInputStream(file);
				try {
					new CopyStream(inputStream).toStream(outputStream);
				} finally {
					IOUtils.close(inputStream);
				}
			}
		};

		beanManager.fireEvent(new AssetRetrieved(assetPath));
		return Response.ok(output, request.getServletContext().getMimeType(assetPath.getPath())).build();
	}

	public Response getIndex(AssetPath assetPath) {
		List<AssetPath> index = assetManager.list(assetPath);
		return Response.ok(new AssetIndex(assetPath, index), MediaType.APPLICATION_XML_TYPE).build();
	}

}
