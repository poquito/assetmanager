package at.poquito.assetmanager.processing;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import at.poquito.assetmanager.Asset;
import at.poquito.assetmanager.AssetPath;
import at.poquito.assetmanager.AssetStream;
import at.poquito.assetmanager.AssetTask;
import at.poquito.assetmanager.tool.FileAssetStream;
import at.poquito.assetmanager.util.CopyStream;

public class DefaultAssetTask implements AssetTask {

	private List<AssetStream> attachments;
	private String correlationId;
	private Asset destination;
	private DefaultAssetTaskHandler handler;
	private Properties properties;
	private Asset source;
	private String taskName;

	public DefaultAssetTask(DefaultAssetTaskHandler handler, String taskName) {
		this.handler = handler;
		this.taskName = taskName;
	}

	protected void addAttachment(AssetStream attachment) {
		if (attachments == null) {
			attachments = new ArrayList<AssetStream>();
		}
		attachments.add(attachment);
	}

	@Override
	public void addAttachment(String name, File attachment) {
		addAttachment(new FileAssetStream(name, attachment));
	}

	@Override
	public void addAttachment(String name, final InputStream attachment) {
		addAttachment(new AssetStream(name) {
			@Override
			public void writeTo(OutputStream outputStream) throws IOException {
				new CopyStream(attachment).toStream(outputStream);
			}
		});
	}
	
	@Override
	public void execute() {
		handler.execute(this, null);
	}


	@Override
	public <R> R execute(Class<R> resultType) {
		return (R) handler.execute(this, resultType);
	}

	@Override
	public void executeAsynchronous() {
		handler.executeAsynchronous(this);
	}

	protected List<AssetStream> getAttachments() {
		if (attachments == null) {
			return Collections.emptyList();
		}
		return attachments;
	}

	String getCorrelationId() {
		return correlationId;
	}

	Asset getDestination() {
		return destination;
	}

	protected Properties getProperties() {
		if (properties == null) {
			return new Properties();
		}
		return properties;
	}

	Asset getSource() {
		return source;
	}

	protected String getTaskName() {
		return taskName;

	}

	@Override
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	@Override
	public void setDestination(AssetPath destination) {
		if (destination == null) {
			return;
		}
		this.destination = handler.getDestinationAsset(destination);
	}

	@Override
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	@Override
	public void setSource(AssetPath source) {
		if (source == null) {
			return;
		}
		this.source = handler.getSourceAsset(source);
	}

}
