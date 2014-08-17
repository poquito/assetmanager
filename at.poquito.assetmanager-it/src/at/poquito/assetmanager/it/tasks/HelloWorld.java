package at.poquito.assetmanager.it.tasks;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import at.poquito.assetmanager.processing.Task;
import at.poquito.assetmanager.processing.TaskContext;

@Named("tutorial.HelloWorld")
@Dependent
public class HelloWorld implements Task {

	@Inject
	private TaskContext context;
	
	@Override
	public Object execute() {
		String input = context.getProperties().getProperty("input");
		return input.toUpperCase();
	}

}
