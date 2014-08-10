package at.poquito.assetmanager.jaxrs.jersey;

import java.io.InputStream;

import javax.activation.DataSource;

import at.poquito.assetmanager.util.IFunction;
import at.poquito.assetmanager.util.IOUtils;

import com.sun.jersey.api.client.WebResource;

class Apply<R> implements IFunction<WebResource, R> {

	public static final <R> Apply<R> forFunction(IFunction<InputStream, R> clientFunction) {
		return new Apply<R>(clientFunction);
	}

	private IFunction<InputStream, R> function;

	Apply(IFunction<InputStream, R> function) {
		this.function = function;

	}

	@Override
	public R apply(WebResource resource) {
		DataSource dataSource = resource.get(DataSource.class);
		InputStream inputStream = IOUtils.getInputStream(dataSource);
		try {
			return function.apply(inputStream);
		} finally {
			IOUtils.close(inputStream);
		}
	}
}