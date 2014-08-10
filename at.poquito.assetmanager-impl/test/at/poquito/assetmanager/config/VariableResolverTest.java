package at.poquito.assetmanager.config;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class VariableResolverTest {

	private Properties variables;

	@Test
	public void must_resolve_variable() {
		VariableResolver resolver = new VariableResolver(variables);
		assertThat(resolver.resolve("${name}"), is("hallo"));
		assertThat(resolver.resolve("${unknown}"), is("${unknown}"));
	}

	@Test
	public void unresolved_variables_must_be_kept() {
		VariableResolver resolver = new VariableResolver(variables);
		assertThat(resolver.resolve("${unknown}"), is("${unknown}"));
	}

	@Test
	public void must_resolve_multible_variables() {
		VariableResolver resolver = new VariableResolver(variables);
		assertThat(resolver.resolve("${name}=${name}"), is("hallo=hallo"));
	}

	@Test
	public void must_not_resovle_escaped_variables() {
		VariableResolver resolver = new VariableResolver(variables);
		assertThat(resolver.resolve("$${name}"), is("${name}"));
	}

	@Before
	public void setup() {
		variables = new Properties();
		variables.setProperty("name", "hallo");
	}

}
