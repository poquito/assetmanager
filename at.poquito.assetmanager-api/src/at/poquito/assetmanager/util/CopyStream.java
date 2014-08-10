package at.poquito.assetmanager.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyStream {

	private InputStream inputStream;
	private long count;

	public CopyStream(InputStream inputStream) {
		this.inputStream = inputStream;
		this.count=0;
	}

	public void toStream(OutputStream outputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int rc = inputStream.read(buffer);
		while (rc != -1) {
			count+=rc;
			if (rc > 0) {
				outputStream.write(buffer, 0, rc);
			}
			rc = inputStream.read(buffer);
		}
		outputStream.flush();
	}

	public void toFile(File file) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(file);

		try {
			toStream(outputStream);
		} finally {
			outputStream.close();
		}
	}
	
	/**
	 * @return the count
	 */
	public long getCount() {
		return count;
	}

}
