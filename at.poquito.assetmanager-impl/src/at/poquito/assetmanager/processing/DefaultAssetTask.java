package at.poquito.assetmanager.processing;

import java.util.Properties;

import at.poquito.assetmanager.Asset;
import at.poquito.assetmanager.AssetPath;
import at.poquito.assetmanager.AssetTask;

public class DefaultAssetTask implements AssetTask {

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
