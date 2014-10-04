package at.poquito.assetmanager.it.server;

import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;

import at.poquito.assetmanager.security.AuthorizationService;
import at.poquito.assetmanager.security.AuthorizationServiceFactory;

@RequestScoped
public class TutorialSecurityServiceFactory implements AuthorizationServiceFactory<HttpServletRequest> {

	public AuthorizationService create(HttpServletRequest request) {
		System.out.println("create authorization service for:" + request.getRequestURI());
		return new AuthorizationService() {

			@Override
			public boolean hasPermission(String permission) {
				boolean hasPermission = "public".equals(permission);
				System.out.println("has public permission:" + hasPermission);
				return hasPermission;
			}

			@Override
			public String currentUser() {
				return "TEST";
			}
		};
	}

}
