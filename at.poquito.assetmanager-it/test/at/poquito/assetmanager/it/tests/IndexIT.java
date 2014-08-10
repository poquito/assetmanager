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
 * integration tests for the index operation.
 * 
 * @author mario.rodler@gmx.net
 * 
 */
public class IndexIT {

	private static JerseyAssetManager assetManager;

	private static File assetManagerDir;

	@BeforeClass
	public static void allTestsSetup() {
		assetManager = AssetManagerIT.createAssetManager();
		assetManagerDir = AssetManagerIT.getAssetManagerDir();
		AssetManagerIT.cleanUpDirectory(assetManagerDir);
		File subdir1 = new File(assetManagerDir, "subdir1");
		File subdir2 = new File(assetManagerDir, "subdir2/subdir");

		subdir1.mkdirs();
		subdir2.mkdirs();

		AssetManagerIT.createTestFile(new File(assetManagerDir, "file1.txt"));
		AssetManagerIT.createTestFile(new File(subdir1, "file2.txt"));
		AssetManagerIT.createTestFile(new File(subdir1, "file3.txt"));
		AssetManagerIT.createTestFile(new File(subdir1, "file4.txt"));
		AssetManagerIT.createTestFile(new File(subdir2, "file5.txt"));
	}

	@Test
	public void list_index_must_contain_directory() {
		AssetPath path = new AssetPath("subdir2/");
		AssetIndex index = assetManager.index(path);
		assertThat(index.getDirectory(), is(path));
		List<AssetPath> entries = index.getEntries();
		assertThat(entries, notNullValue());
		assertThat(entries.get(0), is(new AssetPath("subdir/")));
	}

	@Test
	public void list_index_must_contain_entries() {
		AssetPath path = new AssetPath("subdir1/");
		AssetIndex index = assetManager.index(path);
		assertThat(index.getDirectory(), is(path));
		List<AssetPath> entries = index.getEntries();
		assertThat(entries, notNullValue());
		assertThat(entries.get(0), is(new AssetPath("file2.txt")));
		assertThat(entries.get(1), is(new AssetPath("file3.txt")));
		assertThat(entries.get(2), is(new AssetPath("file4.txt")));
	}

}
