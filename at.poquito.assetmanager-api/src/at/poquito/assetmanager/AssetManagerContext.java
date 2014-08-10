package at.poquito.assetmanager;

public interface AssetManagerContext {
	String username();

	boolean hasPermission(String permission);
}
