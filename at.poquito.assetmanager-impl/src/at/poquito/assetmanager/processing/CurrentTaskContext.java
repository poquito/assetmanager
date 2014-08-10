package at.poquito.assetmanager.processing;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
public class CurrentTaskContext {

	private static ThreadLocal<TaskContext> current = new ThreadLocal<TaskContext>();

	public static void set(TaskContext context) {
		current.set(context);
	}

	public static TaskContext get() {
		return current.get();
	}

	public static void clear() {
		current.set(null);
	}
	
	@Produces
	static TaskContext getCurrentTaskContext(){
		TaskContext taskContext = current.get();
		System.out.println("get current task context:" + taskContext);
		return taskContext;
	}

}
