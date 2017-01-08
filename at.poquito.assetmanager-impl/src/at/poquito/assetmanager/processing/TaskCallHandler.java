package at.poquito.assetmanager.processing;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
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

public class TaskCallHandler {

	private AssetTaskFactory taskFactory;
	private TaskCallParams params;

	public TaskCallHandler(AssetTaskFactory taskFactory, TaskCallParams taskCallParams ) {
		this.taskFactory = taskFactory;
		this.params = taskCallParams;
	}

	public Response execute() {
		String taskName = params.getTaskName();
		AssetTask call = taskFactory.create(taskName);
		call.setProperties(params.getProperties());
		call.setCorrelationId(params.getCorrelationId());
		call.setSource(params.getSource());
		call.setDestination(params.getDestination());
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
			if(o instanceof String){
				return Response.ok(o, MediaType.TEXT_PLAIN).build();
			}
			return Response.ok(o, MediaType.APPLICATION_XML).build();
		} catch (RuntimeException e) {
			Logger log = Logger.getLogger(TaskManager.class.getName());
			String msg = MessageFormat.format("execution of task {0} failed:{1}", taskName, e.getMessage());
			log.log(Level.WARNING, msg, e);
			ResponseBuilder builder = Response.serverError().entity(msg);
			return builder.build();
		}

	}

}
