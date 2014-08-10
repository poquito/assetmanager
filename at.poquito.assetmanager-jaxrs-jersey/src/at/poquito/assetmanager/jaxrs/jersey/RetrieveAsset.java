package at.poquito.assetmanager.jaxrs.jersey;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

import at.poquito.assetmanager.AssetManagerException;
import at.poquito.assetmanager.util.CopyStream;
import at.poquito.assetmanager.util.IFunction;
import at.poquito.assetmanager.util.IOUtils;

import com.sun.jersey.api.client.WebResource;

class RetrieveAsset implements IFunction<WebResource, Object> {
	private final OutputStream outputStream;

	public RetrieveAsset(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	@Override
	public Object apply(WebResource resource) {
		DataSource dataSource = resource.get(DataSource.class);
		InputStream inputStream = IOUtils.getInputStream(dataSource);
		try {
			new CopyStream(inputStream).toStream(outputStream);
			return null;
		} catch (IOException e) {
			throw new AssetManagerException("can't retrieve input stream from datasource");
		} finally {
			IOUtils.close(inputStream);
		}
	}
}