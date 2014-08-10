package at.poquito.assetmanager.processing;

import java.util.Properties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import at.poquito.assetmanager.AssetPath;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskCallParams {
	private String correlationId;
	private AssetPath destination;
	private Properties properties;
	private AssetPath source;
	private String taskName;

	protected TaskCallParams() {
		// tool constructor
	}

	public TaskCallParams(String taskName) {
		this.taskName = taskName;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	/**
	 * @return the destination
	 */
	public AssetPath getDestination() {
		return destination;
	}

	public Properties getProperties() {
		return properties;
	}

	/**
	 * @return the source
	 */
	public AssetPath getSource() {
		return source;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	/**
	 * @param destination
	 *            the destination to set
	 */
	public void setDestination(AssetPath destination) {
		this.destination = destination;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(AssetPath source) {
		this.source = source;
	}
}
