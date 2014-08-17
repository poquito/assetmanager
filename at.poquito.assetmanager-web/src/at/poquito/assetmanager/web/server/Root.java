package at.poquito.assetmanager.web.server;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import at.poquito.assetmanager.AssetTaskFactory;
import at.poquito.assetmanager.processing.MultipartTaskCall;
import at.poquito.assetmanager.rest.RestfulAssetManager;

import com.sun.jersey.multipart.MultiPart;

@Path("/")
@RequestScoped
public class Root {

	@Inject
	private RestfulAssetManager assetManager;

	@Inject
	private AssetTaskFactory taskFactory;

	@Path("/repository")
	public RestfulAssetManager repository() {
		return assetManager;
	}

	@POST
	@Path("/executeTask")
	@Consumes("multipart/mixed")
	public Response executeTask(MultiPart multiPart) throws IOException {
		return new MultipartTaskCall(taskFactory, multiPart).execute();
	}
}
