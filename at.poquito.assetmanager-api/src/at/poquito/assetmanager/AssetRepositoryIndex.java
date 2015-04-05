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
@XmlRootElement(name = "repositories")
@XmlAccessorType(XmlAccessType.FIELD)
public class AssetRepositoryIndex {

	@XmlElement(name = "repository")
	private List<AssetRepository> entries;

	protected AssetRepositoryIndex() {
		// tool constructor
	}

	/**
	 * @param directory
	 * @param entries
	 */
	public AssetRepositoryIndex(List<AssetRepository> entries) {
		this.entries = Collections.unmodifiableList(entries);
	}

	/**
	 * @return the entries
	 */
	public List<AssetRepository> getEntries() {
		if (entries == null) {
			entries = Collections.emptyList();
		}
		return entries;
	}

}
