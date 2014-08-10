package at.poquito.assetmanager;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

public class AssetPathFilterTest {

	@Test
	public void match_everything() {
		assertThat(AssetPathFilter.createPattern("/**"), is("(/.*|$)"));
		assertThat(AssetPathFilter.createPattern("/**/*"), is("(/.*/|/)[^/]+"));
		assertThat(AssetPathFilter.createPattern("**/*"), is("(.*/|)[^/]+"));
	}

	@Test
	public void match_subdir() {
		AssetPathFilter filter = new AssetPathFilter("sample/**");
		assertThat(filter.test("sample/file.txt"), is(true));
		assertThat(filter.test("sample/subdir/file.txt"), is(true));
		assertThat(filter.test("other/file.txt"), is(false));
	}

	@Test
	public void match_subdir_must_match_on_exact_name() {
		AssetPathFilter filter = new AssetPathFilter("/sample/**");
		assertThat(filter.test("/sample"), is(true));
		assertThat(filter.test("/samplesample"), is(false));
	}

	@Test
	public void match_ext() {
		AssetPathFilter filter = new AssetPathFilter("sample/*.*");
		assertThat(filter.test("sample/file.txt"), is(true));
		assertThat(filter.test("sample/file"), is(false));
		assertThat(filter.test("other/file.txt"), is(false));
	}

	@Test
	public void match_other() {
		AssetPathFilter filter = new AssetPathFilter("sample/**/other/*.txt");
		assertThat(filter.test("sample/xx/other/file.txt"), is(true));
		assertThat(filter.test("sample/other/file.txt"), is(true));
		assertThat(filter.test("sample/other/file-txt"), is(false));
		assertThat(filter.test("sample/other/file.png"), is(false));
		assertThat(filter.test("other/other/file.txt"), is(false));
	}

	@Test
	public void match_multiple() {
		AssetPathFilter filter = new AssetPathFilter("**/*.txt, **/*.doc");
		assertThat(filter.test("sample/file.txt"), is(true));
		assertThat(filter.test("sample/file.doc"), is(true));
		assertThat(filter.test("sample/file.something"), is(false));
	}

	@Test
	public void match_for_filter() {
		AssetPathFilter root = new AssetPathFilter("/**");
		AssetPathFilter subdir = new AssetPathFilter("/subdir/**");
		AssetPathFilter subdirfile = new AssetPathFilter("/subdir/*.txt");
		AssetPathFilter txtfile = new AssetPathFilter("/**/*.txt");
		assertThat(root.test(subdir), is(true));
		assertThat(subdir.test(root), is(false));
		assertThat(root.test(subdirfile), is(true));
		assertThat(subdirfile.test(root), is(false));
		assertThat(root.test(txtfile), is(true));
		assertThat(txtfile.test(root), is(false));
		assertThat(subdirfile.test(txtfile), is(false));
		assertThat(txtfile.test(subdirfile), is(true));
		assertThat(txtfile.test(subdir), is(false));
		assertThat(subdirfile.test(txtfile), is(false));

	}

	@Test
	public void compare_must_sort() {
		ArrayList<AssetPathFilter> list = new ArrayList<AssetPathFilter>();
		list.add(new AssetPathFilter("/**"));
		list.add(new AssetPathFilter("/subdir/**"));
		list.add(new AssetPathFilter("/subdir/*.txt"));
		list.add(new AssetPathFilter("/**/*.txt"));
		Collections.sort(list);
		assertThat(list.get(0).expression(), is("/subdir/*.txt"));
		// assertThat(list.get(1).expression(), is("/**/*.txt"));
		// assertThat(list.get(2).expression(), is("/subdir/**"));
		assertThat(list.get(3).expression(), is("/**"));
	}

}
