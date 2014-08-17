package at.poquito.assetmanager.client;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import at.poquito.assetmanager.AssetIndex;
import at.poquito.assetmanager.AssetManagerException;
import at.poquito.assetmanager.security.NotAuthorizedException;
import at.poquito.assetmanager.util.IFunction;

/**
 * a interface for remote access to the assetmanager repositories.
 * 
 * This interface is intended to be used by remote applications to access the
 * repositories of the assetmanager. The set of supported methods is therefore
 * reduced and depending on the infrastructure there can be different
 * implementations.
 * 
 * @param <T>
 *            a type which is used as path parameter.
 * 
 * @author Mario Rodler
 */
public interface AssetManagerClient<T> {

	/**
	 * checks if the path refers an existing resource, without transfering the
	 * resource to the client.
	 * 
	 * the result is also false, if the client does not have the permission to
	 * read the resource.
	 * 
	 * @param path
	 *            the path reference of the resource.
	 * 
	 * @return true if the resource exist.
	 * 
	 * @throws AssetManagerException
	 *             if an unexpected error occurs on the remote server.
	 */
	boolean assetExists(T path);

	/**
	 * reads the directory index from the given path reference.
	 * 
	 * an empty index will be returned, if the directory does not exist or the
	 * client has not the read permission for this directory.
	 * 
	 * @param path
	 *            the directory reference.
	 * 
	 * @return the requested directory index.
	 * 
	 * @throws AssetManagerException
	 *             if an unexpected error occurs on the remote server.
	 */
	AssetIndex index(T path);

	/**
	 * removes the resource or directory referenced by the path object.
	 * 
	 * only empty directories will be removed.
	 * 
	 * @param path
	 *            the path reference of the resource to be deleted.
	 * 
	 * @throws AssetManagerException
	 *             if the path refers to a non-empty directory or if an
	 *             unexpected error occurs on the remote server.
	 * 
	 * @throws NotAuthorizedException
	 *             if the client has no write permission for the repository.
	 */
	void remove(T path);

	/**
	 * reads a resource from a repository and writes its content to the given
	 * output stream.
	 * 
	 * @param path
	 *            the path reference of the resource.
	 * @param outputStream
	 *            a stream which will receive the content of the resource.
	 * 
	 * @throws AssetManagerException
	 *             if an unexpected error occurs on the remote server.
	 * 
	 * @throws NotAuthorizedException
	 *             if the client has no read permission for the repository.
	 */
	void retrieve(T path, OutputStream outputStream);

	/**
	 * reads a resource from a repository and provides it's input stream to an
	 * function object.
	 * 
	 * @param path
	 *            the path reference of the resource.
	 * 
	 * @param function
	 *            a function object which will operate on the inputStream.
	 * 
	 * @return the return value from the function object.
	 * 
	 * @throws AssetManagerException
	 *             if an unexpected error occurs on the remote server.
	 * 
	 * @throws NotAuthorizedException
	 *             if the client has no read permission for the repository.
	 */
	<R> R apply(T path, IFunction<InputStream, R> function);

	/**
	 * stores the content of the input file with the path reference to the
	 * repository.
	 * 
	 * @param path
	 *            the path reference of the new resource.
	 * 
	 * @param inputFile
	 *            a file which content will be transfered to the repository.
	 * 
	 * @throws AssetManagerException
	 *             if an unexpected error occurs on the remote server.
	 * 
	 * @throws NotAuthorizedException
	 *             if the client has no write permission for the repository.
	 */
	void store(T path, File inputFile);

	/**
	 * stores the content of the input stream with the path reference to the
	 * repository.
	 * 
	 * @param path
	 *            the path reference of the new resource.
	 * 
	 * @param inputStream
	 *            a stream which content will be transfered to the repository.
	 * 
	 * @throws AssetManagerException
	 *             if an unexpected error occurs on the remote server.
	 * 
	 * @throws NotAuthorizedException
	 *             if the client has no write permission for the repository.
	 */
	void store(T path, InputStream inputStream);

}