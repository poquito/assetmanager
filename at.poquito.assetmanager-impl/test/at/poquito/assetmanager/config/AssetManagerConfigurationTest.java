package at.poquito.assetmanager.config;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

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
		List<RepositoryConfiguration> repositories = configuration.getRepositories();
		assertThat(repositories.size(), is(1));
		verifyTaskConfiguration(configuration.getTasks());
	}

	private void verifyTaskConfiguration(List<TaskConfiguration> tasks) {
		assertThat(tasks.size(), is(1));
		TaskConfiguration task = tasks.get(0);
		assertThat(task.getName(), is("ResizeImage"));
		assertThat(task.getClassName(), is("at.poquito.processing.ResizeImage"));
		verifyTaskParameter(task.getParameter());
	}

	private void verifyTaskParameter(List<Parameter> parameter) {
		assertThat(parameter.size(), is(2));
		Parameter param = parameter.get(0);
		assertThat(param.getName(), is("width"));
		assertThat(param.getDefaultValue(), is("640"));
		assertThat(param.getType(), is("int"));
	}

}
