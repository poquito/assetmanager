
package at.poquito.assetmanager.event;

import at.poquito.assetmanager.processing.TaskContext;

/**
 * @author mario rodler
 * 
 */
public class TaskExecuted {

	private String taskName;
	private String correlationId;
	private String taskId;

	public TaskExecuted(TaskContext context) {
		this.taskName = context.getTaskName();
		this.taskId = context.getTaskId();
		this.correlationId = context.getCorrelationId();
	}

	public String getTaskName() {
		return taskName;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public String getTaskId() {
		return taskId;
	}

	@Override
	public String toString() {
		return "TaskExecuted [taskName=" + taskName + ", taskId=" + taskId + ", correlationId=" + correlationId + "]";
	}
	
	
}
