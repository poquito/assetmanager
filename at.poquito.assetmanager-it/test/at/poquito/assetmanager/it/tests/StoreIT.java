package at.poquito.assetmanager.it.tests;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import at.poquito.assetmanager.AssetPath;
import at.poquito.assetmanager.jaxrs.jersey.JerseyAssetManager;

/**
 * integration tests for the store operation.
 * 
 * @author mario.rodler@gmx.net
 * 
 */
public class StoreIT {

	private static JerseyAssetManager assetManager;

	private static File assetManagerDir;

	@BeforeClass
	public static void allTestsSetup() {
		AssetManagerIT.cleanUpDirectory(AssetManagerIT.getAssetManagerDir());
		assetManagerDir = AssetManagerIT.getAssetManagerDir();
		assetManager = AssetManagerIT.createAssetManager();
	}

	@Test
	public void store_file_in_root_repository() {
		AssetPath destinationPath = new AssetPath("file1.txt");
		File sourceFile = AssetManagerIT.getTestFile();
		assetManager.store(destinationPath, sourceFile);

		assertThat(new File(assetManagerDir, "file1.txt").exists(), is(true));
	}

	@Test
	public void store_file_in_subdirectory_of_root_repository() {
		AssetPath destinationPath = new AssetPath("subdir1/file1.txt");
		File sourceFile = AssetManagerIT.getTestFile();
		assetManager.store(destinationPath, sourceFile);

		File newSubdirectory = new File(AssetManagerIT.getAssetManagerDir(), "subdir1");
		assertThat(newSubdirectory.exists(), is(true));
		assertThat(new File(newSubdirectory, "file1.txt").exists(), is(true));
	}

	@Test
	public void store_file_in_subdirectory_of_test_repository() {
		AssetPath destinationPath = new AssetPath("admin/subdir2/file1.txt");
		File sourceFile = AssetManagerIT.getTestFile();
		assetManager.store(destinationPath, sourceFile);

		File newFile = new File(AssetManagerIT.getAdminRepositoryDir(), "subdir2/file1.txt");
		assertThat(newFile.exists(), is(true));
	}

}
