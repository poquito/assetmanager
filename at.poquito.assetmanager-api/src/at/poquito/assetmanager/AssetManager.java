package at.poquito.assetmanager;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface AssetManager {

	Asset findAsset(AssetPath path);
	
	Asset getAsset(AssetPath path);

	Asset getAsset(AssetPath path, boolean create);

	void retrieve(AssetPath path, OutputStream outputStream);

	void store(AssetPath path, InputStream inputStream);

	void store(AssetPath path, File inputFile);

	void remove(AssetPath path);

	List<AssetPath> list(AssetPath assetPath);

}
