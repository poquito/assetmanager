
package at.poquito.assetmanager;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import at.poquito.assetmanager.AssetManagerContext;
import at.poquito.assetmanager.security.Permissions;

/**
 * @author mario rodler
 * 
 */
public class PermissionsTest {

	private AssetManagerContext context;

	@Test(expected = NullPointerException.class)
	public void must_throw_exception_if_null() {
		new Permissions(null);
	}

	@Test
	public void empty_means_no_permissions() {
		Permissions permissions = new Permissions("");
		assertThat(permissions.isAuthorized(context), is(Boolean.FALSE));
	}

	@Test
	public void asterisk_means_all_permissions() {
		Permissions permissions = new Permissions("*");
		assertThat(permissions.isAuthorized(context), is(Boolean.TRUE));
	}

	@Test
	public void checks_multiple_permissions() {
		Permissions permissions = new Permissions("p1, p2 ,p3 , px");
		assertThat(permissions.isAuthorized(context), is(Boolean.TRUE));
		verify(context, times(1)).hasPermission("p1");
		verify(context, times(1)).hasPermission("p2");
		verify(context, times(1)).hasPermission("p3");
		verify(context, times(1)).hasPermission("px");
	}

	@Before
	public void setupAssetManagerContext() {
		context = mock(AssetManagerContext.class);
		when(context.hasPermission(anyString())).thenReturn(false);
		when(context.hasPermission("px")).thenReturn(true);
	}

}
