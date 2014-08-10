
package at.poquito.assetmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Mario Rodler
 */
public class Permissions {

	private static final String ALL_PERMISSIONS_STRING = "*";
	private static final String NO_PERMISSIONS_STRING = "";

	private static final Permissions NO_PERMISSIONS = new Permissions(NO_PERMISSIONS_STRING);

	public static final Permissions noPermissions() {
		return NO_PERMISSIONS;
	}

	private final String[] value;

	public Permissions(String permissions) {
		if (permissions == null) {
			throw new NullPointerException();
		}
		List<String> result = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(permissions, ", ");
		while (tokenizer.hasMoreElements()) {
			result.add(tokenizer.nextToken());
		}
		this.value = result.toArray(new String[result.size()]);
	}

	public boolean isAuthorized(AssetManagerContext context) {
		for (int i = 0; i < value.length; i++) {
			if (hasPermission(context, value[i])) {
				return true;
			}
		}
		return false;
	}

	private boolean hasPermission(AssetManagerContext context, String string) {
		if (ALL_PERMISSIONS_STRING.equals(string)) {
			return true;
		}
		return context.hasPermission(string);
	}

	public String[] getValue() {
		return value.clone();
	}

	@Override
	public String toString() {
		return "Permissions [value=" + Arrays.toString(value) + "]";
	}

}
