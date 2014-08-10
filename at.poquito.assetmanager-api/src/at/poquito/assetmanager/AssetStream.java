package at.poquito.assetmanager;

import java.io.IOException;
import java.io.OutputStream;

public abstract class AssetStream {

	private String name;

	public AssetStream(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract void writeTo(OutputStream outputStream) throws IOException;

}
