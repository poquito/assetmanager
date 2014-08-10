package at.poquito.assetmanager.tool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import at.poquito.assetmanager.AssetStream;
import at.poquito.assetmanager.util.CopyStream;
import at.poquito.assetmanager.util.IOUtils;

public class FileAssetStream extends AssetStream {

	private File file;

	public FileAssetStream(String name, File file) {
		super(name);
		this.file = file;
	}

	@Override
	public void writeTo(OutputStream outputStream) throws IOException {
		InputStream inputStream = IOUtils.createInputStream(file);
		try {
			new CopyStream(inputStream).toStream(outputStream);
		} finally {
			IOUtils.close(inputStream);
		}
	}

}
