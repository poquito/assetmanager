package at.poquito.assetmanager.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

import at.poquito.assetmanager.AssetManagerException;

/**
 * @author Mario Rodler
 * 
 */
public class IOUtils {

	public static InputStream getInputStream(DataSource dataSource) {
		try {
			return dataSource.getInputStream();
		} catch (IOException e) {
			throw new AssetManagerException("can't retrieve inputStream from datasource", e);
		}
	}

	public static InputStream createInputStream(File file) {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new AssetManagerException("file not found:" + file.getPath());
		}
	}
	
	public static OutputStream createOutputStream(File file) {
		try {
			return new FileOutputStream(file,false);
		} catch (FileNotFoundException e) {
			throw new AssetManagerException("can't create file output stream:" + file.getPath());
		}
	}

	public static void close(Closeable closeable) {
		try {
			closeable.close();
		} catch (IOException e) {
			throw new AssetManagerException("can't close", e);
		}
	}

	/**
	 * @param sourceFile
	 * @return the content of the file as string
	 */
	public static String readFileAsString(File sourceFile) {
		InputStream inputStream = createInputStream(sourceFile);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			new CopyStream(inputStream).toStream(outputStream);
			return outputStream.toString();
		} catch (IOException e) {
			throw new AssetManagerException("can't read stream", e);
		} finally{
			close(inputStream);
		}
	}
	
}
