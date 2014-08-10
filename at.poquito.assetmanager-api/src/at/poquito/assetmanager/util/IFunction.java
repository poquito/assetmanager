package at.poquito.assetmanager.util;

/**
 * this interface is intended as a simplified replacement for the jdk8 Function
 * interface.
 * 
 * @param <T>
 *            the function argument type.
 * @param <R>
 *            the result type.
 * 
 * @author Mario Rodler
 */
public interface IFunction<T, R> {

	/**
	 * apply a function on the argument T and return R.
	 * 
	 * @param arg an input argument.
	 * @return the result or null.
	 */
	R apply(T arg);

}
