package at.poquito.assetmanager.jaxrs.jersey;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.header.ContentDisposition;

import at.poquito.assetmanager.AssetManagerException;
import at.poquito.assetmanager.AssetStream;
import at.poquito.assetmanager.AssetTask;
import at.poquito.assetmanager.processing.TaskCallParams;
import at.poquito.assetmanager.util.CopyStream;
import at.poquito.assetmanager.util.IOUtils;

public class JerseyTaskManager {

	private WebResource base;

	public JerseyTaskManager(WebResource base) {
		this.base = base;
	}

	@SuppressWarnings("unchecked")
	public <R> R executeTask(TaskCallParams params, Class<R> resultType) {
		final ClientResponse response = base.path("/executeTask").type(MediaType.APPLICATION_XML_TYPE).post(ClientResponse.class, params);
		if (response.getStatus() == 200) {
			if (resultType == null) {
				return null;
			}
			if ((AssetStream.class.isAssignableFrom(resultType))) {
				String fileName = getFileNameFromResponse(response);
				return (R) createStreamResult(response, fileName);
			}
			return response.getEntity(resultType);
		}
		if (response.hasEntity()) {
			throw new AssetManagerException(response.getEntity(String.class));
		}
		throw new AssetManagerException("task execution failed:" + response.getStatus());
	}

	private String getFileNameFromResponse(final ClientResponse response) {
		String value = response.getHeaders().getFirst("Content-Disposition");
		String fileName = "executeTask";
		try {
			ContentDisposition contentDisposition = new ContentDisposition(value);
			fileName = contentDisposition.getFileName();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	private AssetStream createStreamResult(final ClientResponse response, String fileName) {
		return new AssetStream(fileName) {

			@Override
			public void writeTo(OutputStream outputStream) throws IOException {
				InputStream inputStream = response.getEntityInputStream();
				try {
					new CopyStream(inputStream).toStream(outputStream);
				} finally {
					IOUtils.close(inputStream);
				}
			}

		};
	}

	public AssetTask createTaskCall(String taskName) {
		return new JerseyTaskCall(this, new TaskCallParams(taskName));
	}
}
