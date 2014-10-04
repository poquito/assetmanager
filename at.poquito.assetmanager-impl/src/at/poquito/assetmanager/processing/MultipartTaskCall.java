package at.poquito.assetmanager.processing;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;

import at.poquito.assetmanager.AssetStream;
import at.poquito.assetmanager.AssetTask;
import at.poquito.assetmanager.AssetTaskFactory;

import com.sun.jersey.core.header.ContentDisposition;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.MultiPart;

public class MultipartTaskCall {

	private AssetTaskFactory taskFactory;
	private MultiPart multiPart;
	private List<Closeable> resources;

	public MultipartTaskCall(AssetTaskFactory taskFactory, MultiPart multiPart) {
		this.taskFactory = taskFactory;
		this.multiPart = multiPart;
		this.resources = new ArrayList<Closeable>();
	}

	public Response execute() {
		Iterator<BodyPart> iterator = multiPart.getBodyParts().iterator();
		TaskCallParams params = iterator.next().getEntityAs(TaskCallParams.class);
		String taskName = params.getTaskName();
		AssetTask call = taskFactory.create(taskName);
		call.setProperties(params.getProperties());
		call.setCorrelationId(params.getCorrelationId());
		call.setSource(params.getSource());
		call.setDestination(params.getDestination());
		while (iterator.hasNext()) {
			BodyPart part = iterator.next();
			addAttachment(call, part);
		}
		try {
			Object o = call.execute(TaskResult.class);
			if (o == null) {
				return Response.ok().build();
			}
			if (o instanceof AssetStream) {
				final AssetStream assetStream = (AssetStream) o;
				StreamingOutput output = new StreamingOutput() {

					@Override
					public void write(OutputStream outputStream) throws IOException, WebApplicationException {
						assetStream.writeTo(outputStream);
					}
				};
				String contentDisposition = new StringBuilder().append("attachment; filename=\"").append(assetStream.getName())
						.append("\"").toString();
				return Response.ok(output).type(MediaType.APPLICATION_OCTET_STREAM_TYPE).header("Content-Disposition", contentDisposition)
						.build();
			}
			ResponseBuilder builder = Response.ok(o);
			return builder.build();
		} catch (RuntimeException e) {
			Logger log = Logger.getLogger(TaskManager.class.getName());
			String msg = MessageFormat.format("execution of task {0} failed:{1}", taskName, e.getMessage());
			log.log(Level.WARNING, msg, e);
			ResponseBuilder builder = Response.serverError().entity(msg);
			return builder.build();
		} finally {
			closeResources();
		}

	}

	private void closeResources() {
		for (Closeable closeable : resources) {
			try {
				closeable.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}

	private void addAttachment(AssetTask call, BodyPart part) {
		String name = getAttachmentName(part);
		InputStream inputStream = getInputStream(part);
		resources.add(inputStream);
		call.addAttachment(name, inputStream);
	}

	private InputStream getInputStream(BodyPart part) {
		BodyPartEntity entity = (BodyPartEntity) part.getEntity();
		InputStream inputStream = entity.getInputStream();
		return inputStream;
	}

	private String getAttachmentName(BodyPart part) {
		ContentDisposition contentDisposition = part.getContentDisposition();
		String name = contentDisposition.getParameters().get("name");
		return name;
	}
}
