package at.poquito.assetmanager;

import java.io.File;
import java.io.InputStream;

public interface AssetStore {
	
	File get(AssetPath path);

	boolean remove(AssetPath path);

	void retrieveFrom(AssetPath path, InputStream inputStream);

}
