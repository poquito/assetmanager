package at.poquito.assetmanager.it.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import at.poquito.assetmanager.jaxrs.jersey.JerseyAssetManager;
import at.poquito.assetmanager.jaxrs.jersey.JerseyTaskManager;
import at.poquito.assetmanager.util.CopyStream;
import at.poquito.assetmanager.util.IOUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class AssetManagerIT {

	private static final String REPOSITORY_URL = "http://localhost:8080/assetmanager/api/repository";

	static final String ASSETMANAGER_TEST_FILE_CONTENT = "ASSETMANAGER TEST FILE";

	public static void cleanUpDirectory(File dir) {
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				cleanUpDirectory(file);
			}
			file.delete();
		}
	}
	
	public static String getRepositoryUrl() {
		return REPOSITORY_URL;
	}

	public static JerseyAssetManager createAssetManager() {
		URI baseURI = URI.create(REPOSITORY_URL);
		WebResource repository = Client.create().resource(baseURI);
		JerseyAssetManager assetManager = new JerseyAssetManager(repository);
		return assetManager;
	}

	public static JerseyTaskManager createTaskManager() {
		String mediaServerURL = "http://localhost:8080/assetmanager/api";
		WebResource base = Client.create().resource(mediaServerURL);
		JerseyTaskManager taskManager = new JerseyTaskManager(base);
		return taskManager;
	}

	public static void createTestFile(File destination) {
		File file = getTestFile();
		InputStream inputStream = IOUtils.createInputStream(file);
		try {
			new CopyStream(inputStream).toFile(destination);
		} catch (IOException e) {
			fail(e.getMessage());
		} finally {
			IOUtils.close(inputStream);
		}
	}

	public static File getAssetManagerDir() {
		File userHome = new File(System.getProperty("user.home"));
		File assetmanagerDir = new File(userHome, "work/assetmanager");
		if (!assetmanagerDir.exists()) {
			assetmanagerDir.mkdirs();
		}
		return assetmanagerDir;
	}

	public static File getAdminRepositoryDir() {
		return new File(getAssetManagerDir(), "adm");
	}

	public static File getTestFile() {
		URL resource = IndexIT.class.getClassLoader().getResource("resources/sample.txt");
		File file = new File(resource.getFile());
		return file;
	}

}
