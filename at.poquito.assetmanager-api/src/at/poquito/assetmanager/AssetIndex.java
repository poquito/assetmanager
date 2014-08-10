
package at.poquito.assetmanager;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Mario Rodler
 * 
 */
@XmlRootElement(name = "index")
@XmlAccessorType(XmlAccessType.FIELD)
public class AssetIndex {
	private AssetPath directory;

	@XmlElement(name = "entry")
	private List<AssetPath> entries;

	protected AssetIndex() {
		// tool constructor
	}

	/**
	 * @param directory
	 * @param entries
	 */
	public AssetIndex(AssetPath directory, List<AssetPath> entries) {
		this.directory = directory;
		this.entries = Collections.unmodifiableList(entries);
	}

	/**
	 * @return the directory
	 */
	public AssetPath getDirectory() {
		return directory;
	}

	/**
	 * @return the entries
	 */
	public List<AssetPath> getEntries() {
		if (entries == null) {
			entries = Collections.emptyList();
		}
		return entries;
	}

}
