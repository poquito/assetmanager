package at.poquito.assetmanager.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChecksumOutputStream extends OutputStream {
	public enum ChecksumType {
		MD5, SHA1
	}

	public static ChecksumOutputStream fromFile(File file, ChecksumType type) throws IOException {
		return new ChecksumOutputStream(new FileOutputStream(file), type);
	}

	static MessageDigest createMessageDigest(ChecksumType type) {
		try {
			return MessageDigest.getInstance(type.name());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("can't create checksum generator for " + type.name(), e);
		}
	}

	private byte[] checksum;
	private byte[] currentByte;
	private final MessageDigest digest;

	private OutputStream outputStream;

	public ChecksumOutputStream(OutputStream outputStream, ChecksumType type) {
		this.digest = createMessageDigest(type);
		this.outputStream = outputStream;
	}

	public void close() throws IOException {
		checksum = digest.digest();
		outputStream.close();
	};

	public void flush() throws IOException {
		outputStream.flush();
	}

	public byte[] getChecksum() {
		return checksum;
	}

	public String getChecksumAsHexString() {
		String result = "";
		byte[] b = getChecksum();
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	public void write(byte[] buf) throws IOException {
		digest.digest(buf);
		outputStream.write(buf);
	}

	public void write(byte[] buf, int offset, int len) throws IOException {
		try {
			digest.digest(buf, offset, len);
		} catch (DigestException e) {
			throw new IOException("can't compute checksum", e);
		}
		outputStream.write(buf, offset, len);
	}

	public void write(int arg0) throws IOException {
		currentByte[0] = (byte) arg0;
		digest.digest();
		outputStream.write(arg0);
	}

}
