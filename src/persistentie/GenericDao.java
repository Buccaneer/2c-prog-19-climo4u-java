package persistentie;

import java.util.List;

public interface GenericDao<T,K> {

	List<T> getAll();

	/**
	 * 
	 * @param item
	 */
	void insert(T item);

	/**
	 * 
	 * @param item
	 */
	void delete(T item);

	/**
	 * 
	 * @param id
	 */
	T get(K id);

	/**
	 * 
	 * @param id
	 */
	boolean exists(K id);

	/**
	 * 
	 * @param item
	 */
	void update(T item);

}