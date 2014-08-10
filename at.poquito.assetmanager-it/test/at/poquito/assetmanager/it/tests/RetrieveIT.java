package at.poquito.assetmanager.it.tests;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import at.poquito.assetmanager.AssetNotFoundException;
import at.poquito.assetmanager.AssetPath;
import at.poquito.assetmanager.jaxrs.jersey.JerseyAssetManager;
import at.poquito.assetmanager.util.CopyStream;
import at.poquito.assetmanager.util.IFunction;
import at.poquito.assetmanager.util.IOUtils;

/**
 * integration tests for the retrieve operation.
 * 
 * @author mario.rodler@gmx.net
 * 
 */
public class RetrieveIT {

	private static JerseyAssetManager assetManager;

	private static File assetManagerDir;

	@BeforeClass
	public static void allTestsSetup() {
		assetManagerDir = AssetManagerIT.getAssetManagerDir();
		assetManager = AssetManagerIT.createAssetManager();
		AssetManagerIT.cleanUpDirectory(assetManagerDir);

		File subdir1 = new File(assetManagerDir, "subdir1");
		File testFile1 = new File(assetManagerDir, "file1.txt");
		File testFile2 = new File(subdir1, "file2.txt");

		subdir1.mkdirs();

		AssetManagerIT.createTestFile(testFile1);
		AssetManagerIT.createTestFile(testFile2);

		// the private folder is mapped by another repository as 'geheim'
		File privateDir = new File(assetManagerDir, "readonly");
		File privateFile = new File(privateDir, "file3.txt");

		privateDir.mkdirs();
		AssetManagerIT.createTestFile(privateFile);

	}

	@Test
	public void assetExists_must_be_false() {
		AssetPath path = new AssetPath("unknown.txt");
		boolean exists = assetManager.assetExists(path);
		assertThat(exists, is(false));
	}

	@Test
	public void assetExists_must_be_true() {
		AssetPath path = new AssetPath("file1.txt");
		boolean exists = assetManager.assetExists(path);
		assertThat(exists, is(true));
	}

	@Test
	public void retrieve_file_from_alternative_repository() {
		AssetPath path = new AssetPath("public/file3.txt");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		assetManager.retrieve(path, outputStream);
		assertThat(outputStream.toString(), is("Assetmanager Test File"));
	}

	@Test
	public void retrieve_file_from_root_repository() {
		AssetPath path = new AssetPath("file1.txt");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		assetManager.retrieve(path, outputStream);
		assertThat(outputStream.toString(), is("Assetmanager Test File"));
	}

	@Test
	public void retrieve_file_for_processing() {
		AssetPath path = new AssetPath("file1.txt");

		String result = assetManager.apply(path, new IFunction<InputStream, String>() {

			@Override
			public String apply(InputStream arg) {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				try {
					new CopyStream(arg).toStream(outputStream);
				} catch (IOException e) {
					fail("could not read input stream");
				}
				return new String(outputStream.toByteArray());
			}
		});
		assertThat(result, is("Assetmanager Test File"));
	}

	@Test
	public void retrieve_file_in_subdirectory() {
		AssetPath path = new AssetPath("subdir1/file2.txt");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		assetManager.retrieve(path, outputStream);
		assertThat(outputStream.toString(), is("Assetmanager Test File"));
	}

	@Test
	public void retrieve_on_directory_must_reply_index_xml() {
		AssetPath path = new AssetPath("subdir1");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		assetManager.retrieve(path, outputStream);
		assertThat(outputStream.toString(), containsString("<index><directory>/subdir1/</directory><entry>/file2.txt</entry></index>"));
	}

	@Test(expected = AssetNotFoundException.class)
	public void retrieve_on_unknown_file_must_response_with_AssetNotFoundException() {
		AssetPath path = new AssetPath("subdir1/noSuchFile.txt");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		assetManager.retrieve(path, outputStream);
	}

}
