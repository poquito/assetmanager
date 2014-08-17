package at.poquito.assetmanager.config;

import java.io.File;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.enterprise.inject.Alternative;
import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import at.poquito.assetmanager.log.Log;
import at.poquito.assetmanager.log.LogFactory;
import at.poquito.assetmanager.security.Permissions;

@XmlRootElement(name = "assetmanager")
@XmlAccessorType(XmlAccessType.FIELD)
@Alternative
public class AssetManagerConfiguration {

	public static AssetManagerConfiguration readConfiguration(File file) {
		return JAXB.unmarshal(file, AssetManagerConfiguration.class);
	}

	public static AssetManagerConfiguration readConfiguration(URL resource) {
		return JAXB.unmarshal(resource, AssetManagerConfiguration.class);
	}

	@XmlTransient
	private VariableResolver variableResolver;

	@XmlTransient
	private List<Object> assetEventObserver;

	@XmlTransient
	private Log log;

	private String defaultRepository;

	@XmlElement(name = "repository")
	private List<RepositoryConfiguration> nodes;

	@XmlElement(name = "task")
	private List<TaskConfiguration> tasks;

	@XmlElement(name = "audit")
	private String auditClassName;

	public AssetManagerConfiguration() {
		variableResolver = new VariableResolver();
		assetEventObserver = new ArrayList<Object>();
		log = LogFactory.create(getClass());
	}

	public void addRepositoryConfiguration(RepositoryConfiguration repositoryConfiguration) {
		if (nodes == null) {
			nodes = new ArrayList<RepositoryConfiguration>();
		}
		nodes.add(repositoryConfiguration);
	}

	public void addTaskConfiguration(TaskConfiguration taskConfiguration) {
		if (tasks == null) {
			tasks = new ArrayList<TaskConfiguration>();
		}
		tasks.add(taskConfiguration);
	}

	public Permissions buildPermissions(String permissions) {
		if (permissions == null || permissions.isEmpty()) {
			return Permissions.noPermissions();
		}
		return new Permissions(permissions);
	}

	public String defaultRepository() {
		return defaultRepository;
	}

	public List<RepositoryConfiguration> getRepositories() {
		return nodes;
	}

	public List<TaskConfiguration> getTasks() {
		if (tasks == null) {
			return Collections.emptyList();
		}
		return tasks;
	}

	public String resolve(String expression) {
		return variableResolver.resolve(expression);
	}

	public void writeTo(OutputStream outputStream) {
		JAXB.marshal(this, outputStream);
	}

	/**
	 * @return the auditClassName
	 */
	public String getAuditClassName() {
		if (auditClassName != null) {
			if (auditClassName.isEmpty()) {
				return null;
			}
			return auditClassName;
		}
		return null;
	}

	/**
	 * @param massetAudit
	 */
	public void addAssetEventObserver(Object observer) {
		assetEventObserver.add(observer);
	}

	/**
	 * @return the assetEventObserver
	 */
	public List<Object> getAssetEventObserver() {
		return Collections.unmodifiableList(assetEventObserver);
	}
}
