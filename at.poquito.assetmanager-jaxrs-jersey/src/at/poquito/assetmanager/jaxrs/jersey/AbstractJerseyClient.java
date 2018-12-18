package at.poquito.assetmanager.jaxrs.jersey;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

import com.sun.jersey.api.client.WebResource;

import at.poquito.assetmanager.AssetIndex;
import at.poquito.assetmanager.AssetPath;
import at.poquito.assetmanager.client.AssetManagerClient;
import at.poquito.assetmanager.util.IFunction;
import at.poquito.assetmanager.util.IOUtils;

public abstract class AbstractJerseyClient<T> extends JerseyFunctionHandler implements AssetManagerClient<T> {
	private static class RetrieveInputStream implements IFunction<WebResource, InputStream> {

		@Override
		public InputStream apply(WebResource resource) {
			DataSource dataSource = resource.get(DataSource.class);
			return IOUtils.getInputStream(dataSource);
		}
	}

	protected <A> A apply(IFunction<WebResource, A> function, WebResource resource) {
		try {
			return function.apply(resource);
		} catch (Exception e) {
			onError(function, resource, e);
			throw new RuntimeException("execution failed, error not handled", e);
		}
	}

	@Override
	public boolean assetExists(T path) {
		return apply(new AssetExists(), createResource(path));
	}

	public abstract WebResource createResource(T path);

	@Override
	public AssetIndex index(T path) {
		return apply(new GetIndex(), createResource(path));
	}

	@Override
	public void remove(T path) {
		WebResource resource = createResource(path);
		apply(new RemoveAsset(), resource);
	}

	public InputStream retrieve(T path) {
		return apply(new RetrieveInputStream(), createResource(path));
	}

	@Override
	public void retrieve(T path, final OutputStream outputStream) {
		apply(new RetrieveAsset(outputStream), createResource(path));
	}

	@Override
	public void store(T path, File inputFile) {
		InputStream inputStream = IOUtils.createInputStream(inputFile);
		try {
			store(path, inputStream);
		} finally {
			IOUtils.close(inputStream);
		}
	}

	@Override
	public void store(T path, final InputStream inputStream) {
		apply(new StoreAsset(inputStream), createResource(path));
	}

	@Override
	public void copy(T source, AssetPath destination) {
		apply(new CopyAsset(destination), createResource(source));
	}

	
	@Override
	public <R> R apply(T path, IFunction<InputStream, R> function) {
		return apply(Apply.forFunction(function), createResource(path));
	}

}
