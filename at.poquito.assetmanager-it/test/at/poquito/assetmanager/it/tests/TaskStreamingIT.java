package at.poquito.assetmanager.it.tests;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import at.poquito.assetmanager.AssetStream;
import at.poquito.assetmanager.AssetTask;
import at.poquito.assetmanager.jaxrs.jersey.JerseyTaskManager;

/**
 * integration tests for the remove operation.
 * 
 * @author mario.rodler@gmx.net
 * 
 */
public class TaskStreamingIT {
	private static final String TASK_NAME = "IT:AttachmentTask";

	private static File assetManagerDir;
	private static JerseyTaskManager taskManager;

	@BeforeClass
	public static void setup() {
		taskManager = AssetManagerIT.createTaskManager();
		assetManagerDir = AssetManagerIT.getAssetManagerDir();
		AssetManagerIT.cleanUpDirectory(assetManagerDir);
	}

	@Test
	public void send_attachment_and_retrieve_content_as_stream() throws IOException {
		AssetTask task = taskManager.createTaskCall(TASK_NAME);

		ByteArrayInputStream stream = new ByteArrayInputStream("Sample Content".getBytes());
		task.addAttachment("SampleAttachment", stream);

		AssetStream assetStream = task.execute(AssetStream.class);
		assertThat(assetStream.getName(), is("SampleAttachment"));

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		assetStream.writeTo(outputStream);
		assertThat(outputStream.toString(), is("Sample Content"));
	}

}
