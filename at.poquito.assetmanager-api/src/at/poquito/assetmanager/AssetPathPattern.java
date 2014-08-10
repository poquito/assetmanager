package at.poquito.assetmanager;

public class AssetPathPattern {

	private String format;
	private boolean cleanupPath;

	public AssetPathPattern(String expression) {
		cleanupPath = expression.startsWith("[path]");

		format = expression.replace("[path]", "%1$s").replace("[file]", "%2$s").replace("[ext]", "%3$s").replace("[name]", "%4$s");
	}

	public String getFormat() {
		return format;
	}

	public String getPath(String string) {
		String ext = getExt(string);
		return getPath(string, ext);
	}

	public String getPath(String string, String ext) {
		String file = getFile(string);
		String name = getName(file);
		String directory = getDirectory(string);
		String tmpResult = String.format(format, directory, file, ext, name);
		if (directory.length() == 0) {
			tmpResult = tmpResult.replace("//", "/");
		}
		if (cleanupPath && tmpResult.startsWith("/")) {
			return tmpResult.substring(1);
		}
		return tmpResult;
	}

	public static String getFile(String path) {
		int lastSlash = path.lastIndexOf("/");
		if (lastSlash < 0) {
			return path;
		}
		return path.substring(lastSlash + 1);
	}

	public static String getName(String file) {
		int lastDot = file.lastIndexOf(".");
		if (lastDot < 0) {
			return file;
		}
		return file.substring(0, lastDot);
	}

	public static String getExt(String file) {
		int lastDot = file.lastIndexOf(".");
		if (lastDot < 0) {
			return "";
		}
		return file.substring(lastDot + 1);
	}

	public static String getDirectory(String string) {
		int lastSlash = string.lastIndexOf("/");
		if (lastSlash < 1) {
			return "";
		}
		return string.substring(0, lastSlash);
	}
}
