
package at.poquito.assetmanager.it.tests;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.net.URI;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import at.poquito.assetmanager.AssetRepository;
import at.poquito.assetmanager.AssetRepositoryIndex;
import at.poquito.assetmanager.jaxrs.jersey.JerseyAdminClient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * integration tests for the index operation.
 * 
 * @author mario.rodler@gmx.net
 * 
 */
public class RepositoryIndexIT {
	private static final String ADMIN_URL = "http://localhost:8080/assetmanager/api/admin";

	private static JerseyAdminClient adminClient;

	@BeforeClass
	public static void allTestsSetup() {
		URI baseURI = URI.create(ADMIN_URL);
		WebResource adminApi = Client.create().resource(baseURI);
		adminClient = new JerseyAdminClient(adminApi);
	}

	@Test
	public void list_index_must_contain_directory() {
		AssetRepositoryIndex repositoryIndex = adminClient.getRepositoryIndex();
		List<AssetRepository> entries = repositoryIndex.getEntries();
		assertThat(entries, notNullValue());
	}

}
