package at.poquito.assetmanager.jaxrs.jersey;

import java.util.Properties;

import at.poquito.assetmanager.AssetPath;
import at.poquito.assetmanager.AssetTask;
import at.poquito.assetmanager.processing.TaskCallParams;

public class JerseyTaskCall implements AssetTask {

	private JerseyTaskManager jerseyTaskManager;
	private TaskCallParams callArgs;

	public JerseyTaskCall(JerseyTaskManager jerseyTaskManager, TaskCallParams callArgs) {
		this.jerseyTaskManager = jerseyTaskManager;
		this.callArgs = callArgs;
	}


	public void execute() {
		execute(null);
	}

	@Override
	public <R> R execute(Class<R> resultType) {
		return jerseyTaskManager.executeTask(callArgs, resultType);
	}

	@Override
	public void setProperties(Properties properties) {
		callArgs.setProperties(properties);

	}

	@Override
	public void setCorrelationId(String correlationId) {
		callArgs.setCorrelationId(correlationId);
	}

	@Override
	public void setSource(AssetPath source) {
		callArgs.setSource(source);
	}

	@Override
	public void setDestination(AssetPath destination) {
		callArgs.setDestination(destination);
	}

	@Override
	public void executeAsynchronous() {
	}

}
