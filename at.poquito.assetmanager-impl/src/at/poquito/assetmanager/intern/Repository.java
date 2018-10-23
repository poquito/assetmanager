package at.poquito.assetmanager.intern;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import at.poquito.assetmanager.AssetManagerContext;
import at.poquito.assetmanager.AssetManagerException;
import at.poquito.assetmanager.AssetPath;
import at.poquito.assetmanager.AssetPathFilter;
import at.poquito.assetmanager.AssetStore;
import at.poquito.assetmanager.log.Log;
import at.poquito.assetmanager.log.LogFactory;
import at.poquito.assetmanager.security.NotAuthorizedException;
import at.poquito.assetmanager.security.Permissions;
import at.poquito.assetmanager.util.CopyStream;

public class Repository implements Comparable<Repository> {
	public static class Builder {

		private Repository repository;

		Builder(String name, AssetPath path) {
			repository = new Repository(name, path);
		}

		public Repository build() {
			try {
				return repository();
			} finally {
				repository = null;
			}
		}

		public Builder withBaseDir(File baseDir) {
			repository().baseDir = baseDir;
			return this;
		}

		public Builder withFilter(AssetPathFilter filter) {
			repository().filter = filter;
			return this;
		}

		public Builder withReadPermissions(Permissions readPermissions) {
			repository().readPermissions = readPermissions;
			return this;
		}

		public Builder withStore(AssetStore store) {
			repository().store = store;
			return this;
		}

		public Builder withWritePermissions(Permissions writePermissions) {
			repository().writePermissions = writePermissions;
			return this;
		}

		protected Repository repository() {
			if (repository == null) {
				throw new IllegalStateException("repository already build");
			}
			return repository;
		}
	}

	public static Builder withName(String name, AssetPath path) {
		return new Builder(name, path);
	}

	private File baseDir;
	private AssetPathFilter filter;
	private String name;
	private AssetPath path;
	private Permissions readPermissions;
	private AssetStore store;
	private Permissions writePermissions;

	private final Log log;

	public Repository(String name, AssetPath path) {
		this.log = LogFactory.create(getClass());
		this.name = name;
		this.path = path;
		this.filter = new AssetPathFilter(path);
	}

	public boolean accepts(AssetPath other) {
		boolean accepts = filter.test(other.getPath());
		if (accepts) {
			log.debug("repository %s accepted path %s", name, other.getPath());
		}
		return accepts;
	}

	@Override
	public int compareTo(Repository other) {
		return name.compareTo(other.name);
	}

	public File getFile(AssetPath assetPath) {
		return assetPath.pathFromParent(path).toFile(baseDir);
	}

	public String getName() {
		return name;
	}

	public Permissions getReadPermissions() {
		return readPermissions;
	}

	public Permissions getWritePermissions() {
		return writePermissions;
	}

	public final boolean hasReadPermissions(AssetManagerContext context) {
		return readPermissions.isAuthorized(context);
	}

	public final boolean hasWritePermission(AssetManagerContext context) {
		return writePermissions.isAuthorized(context);
	}

	public void remove(AssetManagerContext context, AssetPath destination) {
		if (hasWritePermission(context)) {
			store.remove(destination.pathFromParent(path));
		} else {
			String message = context.username() + " is not authorized to store " + destination.getPath();
			log.info(message);
			throw new NotAuthorizedException(message);
		}
	}

	private InputStream createInputStream(File file) {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new AssetManagerException("could not create input stream from file: " + file);
		}
	}

	public void retrieve(AssetManagerContext context, AssetPath source, OutputStream outputStream) {
		if (hasReadPermissions(context)) {
			File file = store.get(source.pathFromParent(path));
			if (file.isDirectory()) {
				throw new AssetManagerException("can't retrieve directory");
			}
			InputStream inputStream = createInputStream(file);
			try {
				new CopyStream(inputStream).toStream(outputStream);
			} catch (IOException e) {
				throw new AssetManagerException("could not retrieve file:" + file);
			}
		} else {
			String message = context.username() + " is not authorized to retrieve " + source.getPath();
			log.info(message);
			throw new NotAuthorizedException(message);
		}
	}

	public void select(AssetManagerContext context, AssetPath from, List<AssetPath> list) {
		if (!hasReadPermissions(context)) {
			return;
		}
		File dir = getDirectory(from);
		if (!dir.exists()) {
			log.info("requested directory not found %s", dir.getAbsolutePath());
			return;
		}
		int length = dir.getAbsolutePath().length() + 1;
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					list.add(new AssetPath(file.getPath().substring(length) + "/"));
				} else {
					list.add(new AssetPath(file.getPath().substring(length)));
				}
			}
		} else {
			list.add(new AssetPath(dir.getPath().substring(length)));
		}
	}

	public void store(AssetManagerContext context, AssetPath destination, InputStream inputStream) {
		if (hasWritePermission(context)) {
			store.retrieveFrom(destination.pathFromParent(path), inputStream);
		} else {
			String message = context.username() + " is not authorized to store " + destination.getPath();
			log.info(message);
			throw new NotAuthorizedException(message);
		}
	}

	public AssetPath getPath() {
		return path;
	}

	@Override
	public String toString() {
		return "Repository [name=" + name + ", path=" + path + ", baseDir=" + baseDir + "]";
	}

	private File getDirectory(AssetPath from) {
		if (path.isRoot()) {
			return from.toFile(baseDir);
		}
		return from.pathFromParent(path).toFile(baseDir);
	}

}
