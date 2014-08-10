package at.poquito.assetmanager.intern;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import at.poquito.assetmanager.Asset;
import at.poquito.assetmanager.AssetManager;
import at.poquito.assetmanager.AssetManagerContext;
import at.poquito.assetmanager.AssetManagerException;
import at.poquito.assetmanager.AssetNotFoundException;
import at.poquito.assetmanager.AssetPath;
import at.poquito.assetmanager.NotAuthorizedException;
import at.poquito.assetmanager.log.Log;
import at.poquito.assetmanager.log.LogFactory;

public class DefaultAssetManager implements AssetManager {
	private static final Log LOG = LogFactory.create(DefaultAssetManager.class);

	private final AssetManagerContext context;
	private final List<Repository> repositories;

	public DefaultAssetManager(AssetManagerContext context, List<Repository> repositories) {
		this.context = context;
		this.repositories = repositories;
	}

	@Override
	public List<AssetPath> list(AssetPath path) {
		Repository repository = selectRepository(path);
		List<AssetPath> result = new ArrayList<AssetPath>();
		repository.select(context, path, result);
		return result;
	}

	@Override
	public void remove(AssetPath path) {
		LOG.info("remove:" + path);
		selectRepository(path).remove(context, path);
	}

	@Override
	public void retrieve(AssetPath path, OutputStream outputStream) {
		selectRepository(path).retrieve(context, path, outputStream);
	}

	@Override
	public void store(AssetPath path, InputStream inputStream) {
		selectRepository(path).store(context, path, inputStream);
	}

	Repository selectRepository(AssetPath path) {
		for (Repository repository : repositories) {
			if (repository.accepts(path)) {
				return repository;
			}
		}
		throw new AssetManagerException("no repository matches :" + path.getPath());
	}

	@Override
	public Asset getAsset(AssetPath path) {
		return getAsset(path, false);
	}

	@Override
	public void store(AssetPath path, File inputFile) {
		throw new UnsupportedOperationException("store");
	}
	
	public Asset getAsset(AssetPath path, boolean create) {
		Repository repository = selectRepository(path);
		if (!repository.hasReadPermissions(context)) {
			throw new NotAuthorizedException("not authorized to read" + path);
		}
		File file = repository.getFile(path);
		if (file.exists() || create) {
			return new Asset(path, repository.hasWritePermission(context), file);
		}
		throw new AssetNotFoundException(path);
	}
	

	@Override
	public Asset findAsset(AssetPath path) {
		Repository repository = selectRepository(path);
		if (!repository.hasReadPermissions(context)) {
			return null;
		}
		File file = repository.getFile(path);
		if (file.exists()) {
			return new Asset(path, repository.hasWritePermission(context), file);
		}
		return null;
	}
}
