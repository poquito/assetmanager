package at.poquito.assetmanager.it.server;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import at.poquito.assetmanager.AssetManagerContext;

@SessionScoped
public class ClientContext implements AssetManagerContext, Serializable {
	private static final long serialVersionUID = 1L;
	private String username;

	public ClientContext(String username) {
		System.out.println("created new client context for user:" + username);
		this.username = username;
	}

	@Override
	public String username() {
		return username;
	}

	@Override
	public boolean hasPermission(String permission) {
		boolean hasPermission = permission.equals("admin");
		System.out.println("has permission for:" + permission + ":" + hasPermission);
		return hasPermission;
	}
}
