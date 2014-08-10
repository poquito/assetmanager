package at.poquito.assetmanager.processing;

import java.util.Set;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import at.poquito.assetmanager.Asset;
import at.poquito.assetmanager.AssetManager;
import at.poquito.assetmanager.AssetManagerException;
import at.poquito.assetmanager.AssetPath;
import at.poquito.assetmanager.AssetTask;
import at.poquito.assetmanager.AssetTaskFactory;
import at.poquito.assetmanager.NotAuthorizedException;
import at.poquito.assetmanager.event.TaskExecuted;

@ApplicationScoped
public class DefaultAssetTaskHandler implements AssetTaskFactory {

	@Inject
	private AssetManager assetManager;

	@Inject
	private BeanManager beanManager;

	@Override
	public AssetTask create(String taskName) {
		return new DefaultAssetTask(this, taskName);
	}

	@SuppressWarnings("unchecked")
	public <R> R execute(DefaultAssetTask task, Class<R> resultType) {
		String taskId = UUID.randomUUID().toString();
		TaskContext context = new DefaultTaskContext(taskId, task);
		Bean<Task> bean = getBean(task.getTaskName());
		CreationalContext<Task> creationalContext = (CreationalContext<Task>) beanManager.createCreationalContext(bean);
		try {
			CurrentTaskContext.set(context);
			Task instance = (Task) beanManager.getReference(bean, Task.class, creationalContext);
			R result = (R) instance.execute();
			beanManager.fireEvent(new TaskExecuted(context));
			return result;
		} finally {
			CurrentTaskContext.clear();
			creationalContext.release();
		}
	}

	public void executeAsynchronous(DefaultAssetTask task) {
		execute(task, Object.class);
	}

	@SuppressWarnings("unchecked")
	protected Bean<Task> getBean(String name) {
		Set<Bean<?>> taskBeans = beanManager.getBeans(name);
		if (taskBeans.size() > 1) {
			throw new AssetManagerException("ambigious task name:" + name);
		}
		if (taskBeans.size() == 0) {
			throw new AssetManagerException("no task found:" + name);
		}

		return (Bean<Task>) taskBeans.iterator().next();
	}

	Asset getDestinationAsset(AssetPath destination) {
		Asset asset = assetManager.getAsset(destination, true);
		if (asset.isWriteable()) {
			return asset;
		}
		throw new NotAuthorizedException(destination.getPath());
	}

	Asset getSourceAsset(AssetPath source) {
		return assetManager.getAsset(source);
	}

}
