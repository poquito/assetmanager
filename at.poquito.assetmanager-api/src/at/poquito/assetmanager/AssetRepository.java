package at.poquito.assetmanager;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="repository")
@XmlAccessorType(XmlAccessType.FIELD)
public class AssetRepository {

	@XmlAttribute(name = "id", required = true)
	private String id;

	@XmlElement(name = "path", required = true)
	private String path;

	@XmlElement(name = "dir", required = true)
	private String dir;

	@XmlElement(name = "readPermissions")
	private String readPermissions;

	@XmlElement(name = "writePermissions")
	private String writePermissions;

	public String getDir() {
		return dir;
	}

	public String getId() {
		return id;
	}

	public String getPath() {
		return path;
	}

	public String getReadPermissions() {
		return readPermissions;
	}

	public String getWritePermissions() {
		return writePermissions;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setReadPermissions(String readPermissions) {
		this.readPermissions = readPermissions;
	}

	public void setWritePermissions(String writePermissions) {
		this.writePermissions = writePermissions;
	}

}
