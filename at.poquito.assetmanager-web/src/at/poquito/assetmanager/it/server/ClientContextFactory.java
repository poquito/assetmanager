package at.poquito.assetmanager.it.server;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import at.poquito.assetmanager.AssetManagerContext;

@Singleton
public class ClientContextFactory {

	@Produces
	public AssetManagerContext create(){
		return new ClientContext("unauthenticated");
	}
	
}
