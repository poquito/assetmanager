package at.poquito.assetmanager.it.server;

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
import at.poquito.assetmanager.rest.RestfulRepositoryAdmin;

import com.sun.jersey.multipart.MultiPart;

@Path("/")
@RequestScoped
public class Root {

	@Inject
	private RestfulAssetManager assetManager;
	
	@Inject
	private RestfulRepositoryAdmin admin;

	@Inject
	private AssetTaskFactory taskFactory;

	@Path("/repository")
	public RestfulAssetManager repository() {
		return assetManager;
	}
	
	@Path("/admin")
	public RestfulRepositoryAdmin admin() {
		return admin;
	}

	@POST
	@Path("/executeTask")
	@Consumes("multipart/mixed")
	public Response executeTask(MultiPart multiPart) throws IOException {
		return new MultipartTaskCall(taskFactory, multiPart).execute();
	}
}
