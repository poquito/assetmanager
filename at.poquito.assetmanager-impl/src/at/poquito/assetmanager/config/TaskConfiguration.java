
package at.poquito.assetmanager.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskConfiguration {
	@XmlElement
	private String name;
	@XmlElement(name="class")
	private String className;

	@XmlElement(name="param")
	private List<Parameter> parameter;

	protected TaskConfiguration() {
		// tool constructor
	}

	public String getClassName() {
		return className;
	}

	public String getName() {
		return name;
	}

	public List<Parameter> getParameter() {
		return parameter;
	}
}
