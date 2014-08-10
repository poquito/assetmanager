package at.poquito.assetmanager.it.tests;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import at.poquito.assetmanager.client.AssetManagerClient;
import at.poquito.assetmanager.jaxrs.jersey.AbstractJerseyClient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * integration tests for the store operation.
 * 
 * @author mario.rodler@gmx.net
 * 
 */
public class StringAssetManagerIT {

	static class StringJerseyClient extends AbstractJerseyClient<String> {
		
		@Override
		public WebResource createResource(String path) {
			String resourcePath = AssetManagerIT.getRepositoryUrl() + path;
			return Client.create().resource(resourcePath);
		}

	}

	private static AssetManagerClient<String> assetManager;

	private static File assetManagerDir;

	@BeforeClass
	public static void allTestsSetup() {
		AssetManagerIT.cleanUpDirectory(AssetManagerIT.getAssetManagerDir());
		assetManagerDir = AssetManagerIT.getAssetManagerDir();
		assetManager = new StringJerseyClient();
	}

	@Test
	public void store_file_in_root_repository() {
		String file = "/file1.txt";
		File sourceFile = AssetManagerIT.getTestFile();
		assetManager.store(file, sourceFile);
		assertThat(new File(assetManagerDir, "file1.txt").exists(), is(true));
	}

}
