package at.poquito.assetmanager.intern;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.annotation.XmlTransient;

import at.poquito.assetmanager.AssetManager;
import at.poquito.assetmanager.AssetManagerContext;
import at.poquito.assetmanager.AssetPath;
import at.poquito.assetmanager.AssetRepository;
import at.poquito.assetmanager.config.AssetManagerConfiguration;
import at.poquito.assetmanager.config.VariableResolver;
import at.poquito.assetmanager.log.Log;
import at.poquito.assetmanager.log.LogFactory;
import at.poquito.assetmanager.store.FileStore;

@ApplicationScoped
public class AssetManagerFactory {
	private static Log log = LogFactory.create(AssetManagerFactory.class);

	private AssetManagerConfiguration configuration;

	private List<Repository> repositories;

	@XmlTransient
	private VariableResolver variableResolver;

	protected AssetManagerFactory(AssetManagerConfiguration configuration) {
		this.configuration = configuration;
		this.variableResolver = new VariableResolver();
		repositories = createRepositories();
		logConfigurationInfo(log);
	}

	public AssetManager createAssetManager(AssetManagerContext context) {
		return new DefaultAssetManager(context, repositories);
	}

	public List<Repository> createRepositories() {
		List<AssetRepository> nodes = configuration.getRepositories();
		List<Repository> result = new ArrayList<Repository>(nodes.size());
		for (AssetRepository repositoryConfiguration : nodes) {
			result.add(createRepository(repositoryConfiguration));
		}
		Collections.sort(result, new Comparator<Repository>() {
			@Override
			public int compare(Repository o1, Repository o2) {
				return o1.getPath().compareTo(o2.getPath()) * -1;
			}
		});
		return result;
	}

	public void logConfigurationInfo(Log log) {
		log.info("asset manager started.");
		log.info("repositories:");
		for (Repository repository : repositories) {
			log.info(" - %s", repository);
		}
	}

	public String resolve(String value) {
		return variableResolver.resolve(value);
	}

	Repository createRepository(AssetRepository repository) {
		File baseDir = new File(resolve(repository.getDir()));
		return Repository.withName(repository.getId(), new AssetPath(resolve(repository.getPath()))).withBaseDir(baseDir)
				.withReadPermissions(configuration.buildPermissions(repository.getReadPermissions()))
				.withWritePermissions(configuration.buildPermissions(repository.getWritePermissions())).withStore(new FileStore(baseDir))
				.build();

	}

}
