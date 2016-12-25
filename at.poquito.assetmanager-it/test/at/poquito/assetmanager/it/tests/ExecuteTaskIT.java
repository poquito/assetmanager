package at.poquito.assetmanager.it.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

import at.poquito.assetmanager.AssetPath;
import at.poquito.assetmanager.AssetTask;
import at.poquito.assetmanager.jaxrs.jersey.JerseyTaskManager;
import at.poquito.assetmanager.util.IOUtils;

/**
 * integration tests for the remove operation.
 * 
 * @author mario.rodler@gmx.net
 * 
 */
public class ExecuteTaskIT {
	private static final String INPUT_FILE = "input.txt";
	private static final String INPUT_IN_SUBDIR_FILE = "subdir/input.txt";
	private static final String OUTPUT_FILE = "sample/output1.txt";
	private static final String PROPERTY_INPUT = "this is a test";
	private static final String PROPERTY_RESULT = "THIS IS A TEST";
	private static final String TASK_NAME = "IT:ToUpperCase";

	private static File assetManagerDir;
	private static JerseyTaskManager taskManager;

	@Test
	public void execute_by_classname() {
		AssetTask task = taskManager.createTaskCall("tutorial.HelloWorld");
		Properties properties=new Properties();
		properties.setProperty("input", "hello world");
		task.setProperties(properties);
		String string = task.execute(String.class);
	}
	
	@Test
	public void execute_to_upper_case_task_with_file_return_result_object() {
		AssetManagerIT.createTestFile(new File(assetManagerDir, INPUT_FILE));
		AssetTask task = taskManager.createTaskCall(TASK_NAME);
		task.setSource(new AssetPath(INPUT_FILE));
		String result = task.execute(String.class);
		assertThat(result, is(AssetManagerIT.ASSETMANAGER_TEST_FILE_CONTENT));
	}

	@Test
	public void execute_to_upper_case_task_with_file_write_to_repository() {
		AssetManagerIT.createTestFile(new File(assetManagerDir, INPUT_IN_SUBDIR_FILE));
		AssetTask task = taskManager.createTaskCall(TASK_NAME);
		task.setSource(new AssetPath(INPUT_IN_SUBDIR_FILE));
		task.setDestination(new AssetPath(OUTPUT_FILE));
		task.execute();
		File resultFile = new File(assetManagerDir, OUTPUT_FILE);
		assertThat(resultFile.exists(), is(true));
		assertThat(IOUtils.readFileAsString(resultFile), is(AssetManagerIT.ASSETMANAGER_TEST_FILE_CONTENT));
	}

	@Test
	public void execute_to_upper_case_task_with_property_return_result_object() {
		AssetTask task = taskManager.createTaskCall(TASK_NAME);
		Properties properties = new Properties();
		properties.setProperty("text", PROPERTY_INPUT);
		task.setProperties(properties);
		String result = task.execute(String.class);
		assertThat(result, is(PROPERTY_RESULT));
	}

	@Test
	public void execute_to_upper_case_task_with_property_write_to_repository() {
		AssetTask task = taskManager.createTaskCall(TASK_NAME);
		Properties properties = new Properties();
		properties.setProperty("text", PROPERTY_INPUT);
		task.setProperties(properties);
		task.setDestination(new AssetPath(OUTPUT_FILE));
		task.execute();
		File resultFile = new File(assetManagerDir, OUTPUT_FILE);
		assertThat(resultFile.exists(), is(true));
		assertThat(IOUtils.readFileAsString(resultFile), is(PROPERTY_RESULT));
	}

	@BeforeClass
	public static void setup() {
		taskManager = AssetManagerIT.createTaskManager();
		assetManagerDir = AssetManagerIT.getAssetManagerDir();
		AssetManagerIT.cleanUpDirectory(assetManagerDir);
	}

}
