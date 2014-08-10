package at.poquito.assetmanager;

import java.io.File;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlValue;

/**
 * 
 * @author Mario Rodler
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AssetPath implements Serializable, Comparable<AssetPath> {
	private static final long serialVersionUID = 1L;

	private static final AssetPath ROOT = new AssetPath("/");

	@XmlValue
	private String value;

	public AssetPath(String value) {
		if (value == null) {
			throw new IllegalArgumentException("invalid path value:null");
		}
		if (!value.startsWith("/")) {
			value = "/" + value;
		}

		if (value.contains("..")) {
			throw new AssetManagerException("asset path must not contain \"..\".");
		}

		this.value = value;
	}
	
	protected AssetPath() {
		// tool constructor
	}

	public AssetPath append(String path) {
		if (path == null) {
			throw new IllegalArgumentException("invalid path value:null");
		}
		if (isDirectory()) {
			if (path.startsWith("/")) {
				return new AssetPath(value + path.substring(1));
			}
			return new AssetPath(value + path);
		}
		if (path.startsWith("/")) {
			return new AssetPath(value + path);
		}
		return new AssetPath(value + "/" + path);

	}

	public String apply(AssetPathPattern pattern) {
		return pattern.getPath(value);
	}

	@Override
	public int compareTo(AssetPath o) {
		return value.compareTo(o.getPath());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		return value.equals(((AssetPath) obj).value);
	}

	public String getName() {
		return new File(value).getName();
	}

	public AssetPath getParent() {
		if (isRoot()) {
			return null;
		}
		return new AssetPath(new File(value).getParent());
	}

	public String getPath() {
		return value;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	public boolean isDirectory() {
		return value.endsWith("/");
	}

	public boolean isRoot() {
		return "/".equals(value);
	}

	public boolean matches(AssetPathFilter filter) {
		return filter.test(value);
	}

	protected String pathWithoutTrailingSlash() {
		if (isDirectory()) {
			return value.substring(0, value.length() - 1);
		}
		return value;
	}

	public AssetPath pathFromParent(AssetPath parent) {
		if (ROOT.equals(parent)) {
			return this;
		}

		String path = parent.pathWithoutTrailingSlash();
		if (value.startsWith(path)) {
			String root = value.substring(path.length());
			if (root.isEmpty()) {
				return ROOT;
			}
			return new AssetPath(root);
		}
		String message = String.format("path %s is not a parent of %s.", path, value);
		throw new IllegalArgumentException(message);
	}

	public File toFile(File baseDir) {
		return new File(baseDir, getPath());
	}

	@Override
	public String toString() {
		return "AssetPath [value=" + value + "]";
	}

}
