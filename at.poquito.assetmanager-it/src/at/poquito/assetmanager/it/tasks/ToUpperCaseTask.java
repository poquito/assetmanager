package at.poquito.assetmanager.it.tasks;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import at.poquito.assetmanager.Asset;
import at.poquito.assetmanager.AssetManagerException;
import at.poquito.assetmanager.processing.Task;
import at.poquito.assetmanager.processing.TaskContext;
import at.poquito.assetmanager.util.IOUtils;

@Named("IT:ToUpperCase")
@Dependent
public class ToUpperCaseTask implements Task {

	@Inject
	private TaskContext context;

	@Override
	public Object execute() {
		String text = getInputText();
		String result = text.toUpperCase();

		Asset destinationAsset = context.getDestinationAsset();
		if (destinationAsset == null) {
			return result;
		}

		File file = destinationAsset.getFile();
		file.getParentFile().mkdirs();
		OutputStream os = IOUtils.createOutputStream(file);
		try {
			os.write(result.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(os);
		}
		return null;
	}

	private String getInputText() {
		Asset sourceAsset = context.getSourceAsset();
		if (sourceAsset == null) {
			return getContentFromProperties();
		}
		return IOUtils.readFileAsString(sourceAsset.getFile());
	}

	private String getContentFromProperties() {
		Properties properties = context.getProperties();
		String text = properties.getProperty("text");
		if (text == null) {
			throw new AssetManagerException("no input content defined for ToUpperCaseTask");
		}
		return text;
	}

}
