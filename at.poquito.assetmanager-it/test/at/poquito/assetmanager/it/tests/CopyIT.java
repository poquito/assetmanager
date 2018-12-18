package at.poquito.assetmanager.it.tests;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import at.poquito.assetmanager.AssetIndex;
import at.poquito.assetmanager.AssetPath;
import at.poquito.assetmanager.jaxrs.jersey.JerseyAssetManager;

/**
 * integration tests for the copy operation.
 * 
 * @author mario.rodler@gmx.net
 * 
 */
public class CopyIT {

	private static JerseyAssetManager assetManager;

	private static File assetManagerDir;

	@BeforeClass
	public static void allTestsSetup() {
		assetManager = AssetManagerIT.createAssetManager();
		assetManagerDir = AssetManagerIT.getAssetManagerDir();
		AssetManagerIT.createTestFile(new File(assetManagerDir, "a.txt"));
	}

	@Test
	public void list_index_must_contain_directory() {
		AssetPath source = new AssetPath("a.txt");
		AssetPath destination = new AssetPath("b.txt");
		assetManager.copy(source, destination);
	}

}
