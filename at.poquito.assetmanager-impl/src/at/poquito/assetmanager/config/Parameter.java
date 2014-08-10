
package at.poquito.assetmanager.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Parameter {
	@XmlAttribute
	private String name;
	@XmlAttribute(name = "default")
	private String defaultValue;
	@XmlAttribute
	private String type;

	protected Parameter() {
		// tool constructor
	}

	public Parameter(String name, String defaultValue, String type) {
		this.name = name;
		this.defaultValue = defaultValue;
		this.type = type;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Parameter [name=" + name + ", defaultValue=" + defaultValue + ", type=" + type + "]";
	}

}
