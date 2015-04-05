package at.poquito.assetmanager.rest;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import at.poquito.assetmanager.AssetRepository;
import at.poquito.assetmanager.AssetRepositoryIndex;


public class RestfulRepositoryAdmin {

	@GET
	@Path("repositories")
	public AssetRepositoryIndex getRepositoryIndex(){
		List<AssetRepository> emptyList = Collections.emptyList();
		AssetRepositoryIndex index = new AssetRepositoryIndex(emptyList);
		return index;
	}
}
