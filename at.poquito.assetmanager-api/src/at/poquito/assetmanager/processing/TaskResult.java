package at.poquito.assetmanager.processing;

import java.io.OutputStream;

public class TaskResult {

	private String taskId;

	public TaskResult(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void write(OutputStream outputStream) {
		throw new UnsupportedOperationException("task result contains no content stream");
	}
}
