package at.poquito.assetmanager;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class AssetPatternTest {

	@Test
	public void test() {
		AssetPathPattern pattern = new AssetPathPattern("[ext]");
		assertThat(pattern.getPath("image/input.txt"), is("txt"));
		pattern = new AssetPathPattern("[name]");
		assertThat(pattern.getPath("image/input.txt"), is("input"));
		pattern = new AssetPathPattern("[file]");
		assertThat(pattern.getPath("image/input.txt"), is("input.txt"));
		pattern = new AssetPathPattern("[path]");
		assertThat(pattern.getPath("image/input.txt"), is("image"));
		pattern = new AssetPathPattern("otherDir/[path]/[file]");
		assertThat(pattern.getPath("input.txt"), is("otherDir/input.txt"));
		pattern = new AssetPathPattern("[path]/[file]");
		assertThat(pattern.getPath("input.txt"), is("input.txt"));
		pattern = new AssetPathPattern("[path]/[name].[ext]");
		assertThat(pattern.getPath("input.txt"), is("input.txt"));
		pattern = new AssetPathPattern("[path]/[name]-tumbnail.[ext].part");
		assertThat(pattern.getPath("input.txt"), is("input-tumbnail.txt.part"));
	}

	@Test
	public void getDirectory() {
		assertThat(AssetPathPattern.getDirectory("one/two"), is("one"));
		assertThat(AssetPathPattern.getDirectory("one"), is(""));
	}

	@Test
	public void getFile() {
		assertThat(AssetPathPattern.getFile("one/two"), is("two"));
		assertThat(AssetPathPattern.getFile("one"), is("one"));
	}

	@Test
	public void getName() {
		assertThat(AssetPathPattern.getName("one.two"), is("one"));
		assertThat(AssetPathPattern.getName("one"), is("one"));
	}

	@Test
	public void getExt() {
		assertThat(AssetPathPattern.getExt("one.two"), is("two"));
		assertThat(AssetPathPattern.getExt("one"), is(""));
	}

}
