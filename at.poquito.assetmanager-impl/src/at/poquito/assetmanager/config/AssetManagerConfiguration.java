package at.poquito.assetmanager.config;

import java.io.File;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Alternative;
import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import at.poquito.assetmanager.AssetManagerException;
import at.poquito.assetmanager.AssetRepository;
import at.poquito.assetmanager.log.Log;
import at.poquito.assetmanager.log.LogFactory;
import at.poquito.assetmanager.security.Permissions;

@XmlRootElement(name = "assetmanager")
@XmlAccessorType(XmlAccessType.FIELD)
@Alternative
public class AssetManagerConfiguration {

	public static AssetManagerConfiguration readConfiguration(File file) {
		return JAXB.unmarshal(file, AssetManagerConfiguration.class);
	}

	public static AssetManagerConfiguration readConfiguration(URL resource) {
		return JAXB.unmarshal(resource, AssetManagerConfiguration.class);
	}

	private String defaultRepository;

	@XmlTransient
	private Log log;

	@XmlElement(name = "repository")
	private List<AssetRepository> nodes;

	private String adminPermission;

	public AssetManagerConfiguration() {
		log = LogFactory.create(getClass());
	}

	public void addRepositoryConfiguration(AssetRepository repositoryConfiguration) {
		if (nodes == null) {
			nodes = new ArrayList<AssetRepository>();
		}
		for (AssetRepository repository : nodes) {
			if (repository.getId().equals(repositoryConfiguration.getId())) {
				throw new AssetManagerException("can't add repository. duplicate id:" + repositoryConfiguration.getId());
			}
		}
		nodes.add(repositoryConfiguration);
	}

	public void storeRepositoryConfiguration(AssetRepository repositoryConfiguration) {
		if (nodes == null) {
			nodes = new ArrayList<AssetRepository>();
		}
		for (AssetRepository repository : nodes) {
			if (repository.getId().equals(repositoryConfiguration.getId())) {
				nodes.remove(repositoryConfiguration);
			}
		}
		nodes.add(repositoryConfiguration);
	}

	public AssetRepository findRepository(String id) {
		if (nodes != null) {
			for (AssetRepository repository : nodes) {
				if (repository.getId().equals(id)) {
					return repository;
				}
			}
		}
		return null;
	}

	public void removeRepositoryConfiguration(AssetRepository repositoryConfiguration) {
		if (nodes != null) {
			for (AssetRepository repository : nodes) {
				if (repository.getId().equals(repositoryConfiguration.getId())) {
					nodes.remove(repositoryConfiguration);
				}
			}
		}
	}

	public Permissions buildPermissions(String permissions) {
		if (permissions == null || permissions.isEmpty()) {
			return Permissions.noPermissions();
		}
		return new Permissions(permissions);
	}

	public String defaultRepository() {
		return defaultRepository;
	}

	public List<AssetRepository> getRepositories() {
		return nodes;
	}

	public String getAdminPermission() {
		return adminPermission;
	}

	public void writeTo(OutputStream outputStream) {
		try {
			JAXBContext instance = JAXBContext.newInstance(AssetManagerConfiguration.class);
			Marshaller marshaller = instance.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(this, outputStream);
		} catch (PropertyException e) {
			throw new DataBindingException(e);
		} catch (JAXBException e) {
			throw new DataBindingException(e);
		}
	}

	public void writeTo(File configFile) {
		try {
			JAXBContext instance = JAXBContext.newInstance(AssetManagerConfiguration.class);
			Marshaller marshaller = instance.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(this, configFile);
		} catch (PropertyException e) {
			throw new DataBindingException(e);
		} catch (JAXBException e) {
			throw new DataBindingException(e);
		}
	}
}
