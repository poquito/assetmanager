package at.poquito.assetmanager.store;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import at.poquito.assetmanager.AssetManagerException;
import at.poquito.assetmanager.AssetNotFoundException;
import at.poquito.assetmanager.AssetPath;
import at.poquito.assetmanager.AssetStore;
import at.poquito.assetmanager.log.Log;
import at.poquito.assetmanager.log.LogFactory;
import at.poquito.assetmanager.util.CopyStream;

public class FileStore implements AssetStore {
	private final File baseDir;
	private final Log log;

	public FileStore(File baseDir) {
		this.baseDir = baseDir;
		this.log = LogFactory.create(getClass());
	}

	public File get(AssetPath path) {
		File fileFromPath = fileFromPath(path);
		if (fileFromPath.exists()) {
			return fileFromPath;
		}
		throw new AssetNotFoundException(path);
	}

	@Override
	public boolean remove(AssetPath path) {
		File file = fileFromPath(path);
		if (file.exists()) {
			if(file.isDirectory()){
				return removeDirectory(file);
			}
			return file.delete();
		}
		log.debug("%s not removed: file does not exist", path.getPath());
		return false;
	}

	private boolean removeDirectory(File dir) {
		if(dir.listFiles().length!=0){
			throw new AssetManagerException("directory not empty!");
		}
		return dir.delete();
	}

	@Override
	public void retrieveFrom(AssetPath path, InputStream inputStream) {
		if (path.isRoot()) {
			throw new AssetManagerException("invalid path: can't store file as repository root");
		}
		createDirectories(path);
		try {
			new CopyStream(inputStream).toFile(fileFromPath(path));
		} catch (IOException e) {
			String message = String.format("can't store asset %s", path.getPath());
			throw new AssetManagerException(message, e);
		}
	}

	private void createDirectories(AssetPath path) {
		if(!baseDir.exists()){
			baseDir.mkdirs();
		}
		AssetPath parent = path.getParent();
		if (!parent.isRoot()) {
			File directory = new File(baseDir, parent.getPath());
			if (!directory.exists()) {
				directory.mkdirs();
			}
		}
	}

	File fileFromPath(AssetPath path) {
		File file = new File(baseDir, path.getPath());
		log.debug("file from path %s:  %s", path, file);
		return file;
	}

}
