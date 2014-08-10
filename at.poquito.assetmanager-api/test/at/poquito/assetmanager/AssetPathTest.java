package at.poquito.assetmanager;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class AssetPathTest {

	@Test
	public void append_paths_create_valid_seperators_with_file() {
		AssetPath path = new AssetPath("/parent");
		assertThat(path.append("sub").getPath(), is("/parent/sub"));
		assertThat(path.append("/sub").getPath(), is("/parent/sub"));
	}

	@Test
	public void append_paths_create_valid_seperators_with_directory() {
		AssetPath path = new AssetPath("/parent/");
		assertThat(path.append("sub").getPath(), is("/parent/sub"));
		assertThat(path.append("/sub").getPath(), is("/parent/sub"));
	}
	
	@Test
	public void get_name_returns_last_fragment_or_empty_string() {
		assertThat(new AssetPath("/").getName(), is(""));
		assertThat(new AssetPath("/parent").getName(), is("parent"));
		assertThat(new AssetPath("/parent/").getName(), is("parent"));
	}

	@Test
	public void get_parent_path() {
		assertThat(new AssetPath("/parent1").getParent().getPath(), is("/"));
		assertThat(new AssetPath("/parent1/").getParent().getPath(), is("/"));
		assertThat(new AssetPath("/parent2/sub").getParent().getPath(), is("/parent2"));
	}

	@Test()
	public void must_convert_to_absolute_path() {
		assertThat(new AssetPath("path").getPath(), is("/path"));
		assertThat(new AssetPath("path/").getPath(), is("/path/"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void must_not_accept_null_value() {
		String nullString = null;
		new AssetPath(nullString);
	}

	@Test
	public void must_return_path_value() {
		assertThat(new AssetPath("/path").getPath(), is("/path"));
		assertThat(new AssetPath("/path/").getPath(), is("/path/"));
	}

	@Test
	public void must_return_subdir_from_common_parent() {
		AssetPath subdir = new AssetPath("/parent/subdir");
		assertThat(subdir.pathFromParent(new AssetPath("/parent")).getPath(), is("/subdir"));
		assertThat(subdir.pathFromParent(new AssetPath("/parent/")).getPath(), is("/subdir"));
	}

	@Test
	public void parent_of_root_must_be_null() {
		AssetPath root = new AssetPath("/");
		assertTrue(root.isRoot());
		assertThat(root.getParent(), is(nullValue()));
	}

	@Test
	public void pathFromParent_must_return_root() {
		assertThat(new AssetPath("/parent").pathFromParent(new AssetPath("/parent")).getPath(), is("/"));
		assertThat(new AssetPath("/parent/").pathFromParent(new AssetPath("/parent")).getPath(), is("/"));
		assertThat(new AssetPath("/parent/").pathFromParent(new AssetPath("/parent/")).getPath(), is("/"));
		assertThat(new AssetPath("/parent").pathFromParent(new AssetPath("/parent/")).getPath(), is("/"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void pathFromParent_must_throw_exception_if_path_is_not_parent() {
		AssetPath parent = new AssetPath("/other");
		AssetPath subdir = new AssetPath("/parent/subdir");
		subdir.pathFromParent(parent);
	}

	@Test
	public void pathFromParent_same_if_parent_is_root() {
		AssetPath parent = new AssetPath("/");
		AssetPath subdir = new AssetPath("/parent");
		assertThat(subdir.pathFromParent(parent).getPath(), is("/parent"));
	}

	@Test
	public void paths_are_equal() {
		AssetPath path = new AssetPath("/path");
		assertThat(new AssetPath("/path"), is(path));
	}

	@Test
	public void paths_that_end_with_slash_are_directories() {
		assertThat(new AssetPath("/path/").isDirectory(), is(true));
	}

}
