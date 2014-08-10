package at.poquito.assetmanager.it.tasks;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import at.poquito.assetmanager.AssetManagerException;
import at.poquito.assetmanager.AssetStream;
import at.poquito.assetmanager.processing.Task;
import at.poquito.assetmanager.processing.TaskContext;
import at.poquito.assetmanager.util.CopyStream;

@Named("IT:AttachmentTask")
public class AttachmentTask implements Task {

	@Inject
	private TaskContext context;

	@Override
	public Object execute() {
		List<AssetStream> attachments = context.getAttachments();
		if (attachments.isEmpty()) {
			throw new AssetManagerException("attachment is required");
		}

		// we retrieve the first attachment and send it back as result
		final AssetStream attachment = attachments.get(0);
		System.out.println("retrieved attachment:" + attachment.getName());
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			attachment.writeTo(outputStream);
		} catch (IOException e) {
			throw new AssetManagerException("can't copy attachment", e);
		}
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

		return new AssetStream(attachment.getName()) {
			@Override
			public void writeTo(OutputStream outputStream) throws IOException {
				new CopyStream(inputStream).toStream(outputStream);
			}
		};

	}

}
