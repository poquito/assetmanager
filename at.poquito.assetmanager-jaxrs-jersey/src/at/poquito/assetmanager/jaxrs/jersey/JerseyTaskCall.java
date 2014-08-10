package at.poquito.assetmanager.jaxrs.jersey;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.core.MediaType;

import at.poquito.assetmanager.AssetPath;
import at.poquito.assetmanager.AssetTask;
import at.poquito.assetmanager.processing.TaskCallParams;

import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.MultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import com.sun.jersey.multipart.file.StreamDataBodyPart;

public class JerseyTaskCall implements AssetTask {

	private JerseyTaskManager jerseyTaskManager;
	private TaskCallParams callArgs;
	private List<BodyPart> attachments;

	public JerseyTaskCall(JerseyTaskManager jerseyTaskManager, TaskCallParams callArgs) {
		this.jerseyTaskManager = jerseyTaskManager;
		this.callArgs = callArgs;
		this.attachments = new ArrayList<BodyPart>();
	}

	@Override
	public void addAttachment(String name, File attachment) {
		FileDataBodyPart part = new FileDataBodyPart(name, attachment);
		attachments.add(part);
	}

	@Override
	public void addAttachment(String name, InputStream attachment) {
		StreamDataBodyPart part = new StreamDataBodyPart(name, attachment, name);
		attachments.add(part);
	}

	public void execute() {
		execute(null);
	}

	@Override
	public <R> R execute(Class<R> resultType) {
		MultiPart form = new MultiPart();
		form.bodyPart(new BodyPart(callArgs, MediaType.APPLICATION_XML_TYPE));
		for (BodyPart part : attachments) {
			form.bodyPart(part);
		}
		return jerseyTaskManager.executeTask(form, resultType);
	}

	@Override
	public void setProperties(Properties properties) {
		callArgs.setProperties(properties);

	}

	@Override
	public void setCorrelationId(String correlationId) {
		callArgs.setCorrelationId(correlationId);
	}

	@Override
	public void setSource(AssetPath source) {
		callArgs.setSource(source);
	}

	@Override
	public void setDestination(AssetPath destination) {
		callArgs.setDestination(destination);
	}

	@Override
	public void executeAsynchronous() {
	}

}
