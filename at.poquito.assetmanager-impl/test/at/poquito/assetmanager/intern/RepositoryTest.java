
package at.poquito.assetmanager.intern;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Matchers;

import at.poquito.assetmanager.AssetManagerContext;
import at.poquito.assetmanager.AssetPath;
import at.poquito.assetmanager.AssetStore;
import at.poquito.assetmanager.security.Permissions;

/**
 * @author mario rodler
 * 
 */
public class RepositoryTest {
	/**
	 * 
	 */
	private static final String SUBDIR = "/subdir";
	private static final String USERID = "userid";
	private static final Permissions READ_PERMISSIONS = new Permissions("read");
	private static final Permissions WRITE_PERMISSIONS = new Permissions("write");
	private static final String REPOSITORY = "Repository";
	private AssetStore store;
	private AssetManagerContext context;

	@Test
	public void repository_must_accept_path() {
		Repository repository = createRootRepository();
		assertThat(repository.accepts(new AssetPath("x")), is(Boolean.TRUE));
	}

	@Test
	public void repository_must_be_readable() {
		createRootRepository();
	}

	@Test
	public void repository_must_be_writeable() {
		Repository repository = createRootRepository();
		assertThat(repository.accepts(new AssetPath("x")), is(Boolean.TRUE));
	}

	@Test
	public void repository_getFileFromSubdir() {
		Repository repository = createSubRepository();
		File f = repository.getFile(new AssetPath(SUBDIR + "/x/x.jpg"));
		System.out.println(f);
	}

	@Test
	@Ignore //FIXME: test geht nicht mehr, seit geprüft wird ob Ziel ein Verzeichnis ist!
	public void repository_retrieve_from_subdir_must_remove_root_path() {
		Repository repository = createSubRepository();
		when(store.get(Matchers.any(AssetPath.class))).thenReturn(new File("anyFile"));
		repository.retrieve(context, new AssetPath(SUBDIR + "/x/x.jpg"), new ByteArrayOutputStream());
		verify(store).get(new AssetPath("/x/x.jpg"));
	}

	@Test
	public void repository_store_from_subdir_must_remove_root_path() {
		ByteArrayInputStream inputStream = new ByteArrayInputStream("demo".getBytes());
		Repository repository = createSubRepository();
		repository.store(context, new AssetPath(SUBDIR + "/x/x.jpg"), inputStream);
		verify(store).retrieveFrom(new AssetPath("/x/x.jpg"), inputStream);
	}

	public Repository createRootRepository() {
		AssetPath path = new AssetPath("/");

		return Repository.withName(REPOSITORY, path).withReadPermissions(READ_PERMISSIONS).withWritePermissions(WRITE_PERMISSIONS)
				.withStore(store).build();
	}

	public Repository createSubRepository() {
		AssetPath path = new AssetPath(SUBDIR);

		return Repository.withName(REPOSITORY, path).withReadPermissions(READ_PERMISSIONS).withWritePermissions(WRITE_PERMISSIONS)
				.withStore(store).build();
	}

	@Before
	public void setup() {
		store = mock(AssetStore.class);
		context = mock(AssetManagerContext.class);
		when(context.hasPermission(anyString())).thenReturn(true);
		when(context.username()).thenReturn(USERID);
	}
}
