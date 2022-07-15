package co.com.biciu.interfaces;

import java.util.List;

/**
 * This interface defines all the methods required to execute CRUD operations.
 * @param <T> The class you want to persist instances of.
 * @param <K> The datatype of the unique property that gives identity to every instance of the given class.
 */
public interface Repository<T, K> {
    List<T> findAll();
    T findById(K id);
    T save(T object);
    T update(K id, T updatedObject);
    Boolean delete(K id);
}
