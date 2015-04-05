package at.poquito.assetmanager.config;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import at.poquito.assetmanager.AssetRepository;
import at.poquito.assetmanager.security.Permissions;

public class AssetManagerConfigurationTest {

	@Test
	public void test() {
		Permissions permissions = new AssetManagerConfiguration().buildPermissions("admin");
		assertThat(Arrays.toString(permissions.getValue()), is("[admin]"));
	}

	@Test
	public void read_config_from_resource() {
		URL resource = AssetManagerConfiguration.class.getResource("assetmanager.xml");
		AssetManagerConfiguration configuration = AssetManagerConfiguration.readConfiguration(resource);
		List<AssetRepository> repositories = configuration.getRepositories();
		assertThat(repositories.size(), is(1));
	}

}
