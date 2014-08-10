package at.poquito.assetmanager.processing;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class DefaultTaskCallTest {

	@Test
	public void null_initialization_throws_no_exception() {
		DefaultAssetTaskHandler taskHandler = new DefaultAssetTaskHandler() {
			@Override
			public <R> R execute(DefaultAssetTask task, Class<R> resultType) {
				assertThat(task.getTaskName(), is("sampleTask"));
				assertThat(task.getProperties(), notNullValue());
				assertThat(task.getAttachments(), notNullValue());
				return null;
			}
		};
		DefaultAssetTask call = new DefaultAssetTask(taskHandler, "sampleTask");
		call.setCorrelationId(null);
		call.setDestination(null);
		call.setSource(null);
		call.addAttachment(null);
		call.execute(String.class);
	}

}
