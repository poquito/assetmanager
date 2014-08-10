
package at.poquito.assetmanager;

import java.io.File;

/**
 * @author Mario Rodler
 */
public class Asset {
	private AssetPath path;
	private boolean writeable;
	private File file;

	public Asset(AssetPath path, boolean writeable, File file) {
		this.path = path;
		this.writeable = writeable;
		this.file = file;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @return the writeable
	 */
	public boolean isWriteable() {
		return writeable;
	}

	/**
	 * @return the path
	 */
	public AssetPath getPath() {
		return path;
	}

	/**
	 * @return the length
	 */
	public long getLength() {
		return file.length();
	}

}
