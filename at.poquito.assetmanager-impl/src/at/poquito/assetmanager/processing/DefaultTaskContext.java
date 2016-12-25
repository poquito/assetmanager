package at.poquito.assetmanager.processing;

import java.util.Properties;

import at.poquito.assetmanager.Asset;

public class DefaultTaskContext implements TaskContext {
	private String correlationId;
	private Asset destinationAsset;
	private Properties properties;
	private Asset sourceAsset;
	private final String taskId;

	private String taskName;

	public DefaultTaskContext(String taskId, DefaultAssetTask task) {
		this.taskId = taskId;
		this.taskName = task.getTaskName();
		this.correlationId = task.getCorrelationId();
		this.destinationAsset = task.getDestination();
		this.properties = task.getProperties();
		this.sourceAsset = task.getSource();
	}

	/**
	 * @return the correlationId
	 */
	public String getCorrelationId() {
		return correlationId;
	}

	/**
	 * @return the destinationFile
	 */
	public Asset getDestinationAsset() {
		return destinationAsset;
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * @return the sourceFile
	 */
	public Asset getSourceAsset() {
		return sourceAsset;
	}

	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	@Override
	public String toString() {
		return "TaskContext [taskName=" + taskName + ", taskId=" + taskId + ", correlationId=" + correlationId + ", properties="
				+ properties + "]";
	}

}
