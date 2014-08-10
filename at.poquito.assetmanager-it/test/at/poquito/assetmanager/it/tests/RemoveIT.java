package at.poquito.assetmanager.it.tests;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import at.poquito.assetmanager.AssetPath;
import at.poquito.assetmanager.jaxrs.jersey.JerseyAssetManager;

/**
 * integration tests for the remove operation.
 * 
 * @author mario.rodler@gmx.net
 * 
 */
public class RemoveIT {

	private static JerseyAssetManager assetManager;

	private static File assetManagerDir;

	@BeforeClass
	public static void allTestsSetup() {
		assetManagerDir = AssetManagerIT.getAssetManagerDir();
		assetManager = AssetManagerIT.createAssetManager();
		AssetManagerIT.cleanUpDirectory(assetManagerDir);
	}

	@Test
	public void remove_file() {
		File destination = new File(assetManagerDir, "file1.txt");
		AssetManagerIT.createTestFile(destination);

		AssetPath path = new AssetPath("file1.txt");
		assetManager.remove(path);

		assertThat(destination.exists(), is(false));
	}

	@Test
	public void remove_directory() {
		File subdir2 = new File(assetManagerDir, "subdir2/subdir");
		subdir2.mkdirs();

		AssetPath path = new AssetPath("subdir2/subdir");
		assetManager.remove(path);

		assertThat(subdir2.exists(), is(false));
	}

}
