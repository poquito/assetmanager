package at.poquito.assetmanager.tool;

import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class ChecksumGenerator {

	public static final ChecksumGenerator NULL_GENERATOR = new ChecksumGenerator() {
		@Override
		public void add(byte[] input, int len) {
			// do nothing
		}

		@Override
		public String complete() {
			return "";
		}
	};

	public static final ChecksumGenerator createMD5ChecksumGenerator() {
		final MessageDigest digest = createMessageDigest(Type.MD5);
		return createDigestChecksumGenerator(digest);
	}

	public static final ChecksumGenerator createSHA1ChecksumGenerator() {
		final MessageDigest digest = createMessageDigest(Type.SHA1);
		return createDigestChecksumGenerator(digest);
	}

	public static ChecksumGenerator createDigestChecksumGenerator(final MessageDigest digest) {
		return new ChecksumGenerator() {
			public void add(byte[] input, int len) {
				try {
					digest.digest(input, 0, len);
				} catch (DigestException e) {
					throw new RuntimeException("failed to digest input", e);
				}
			}

			@Override
			public String complete() {
				return getChecksumAsHex(digest.digest());
			}
		};
	}

	public static MessageDigest createMessageDigest(Type type) {
		try {
			return MessageDigest.getInstance(type.name());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("can't create checksum generator for " + type.name(), e);
		}
	}

	public enum Type {
		MD5, SHA1
	}

	public static String getChecksumAsHex(byte[] b) {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	public abstract void add(byte[] input, int len);

	public abstract String complete();

}
