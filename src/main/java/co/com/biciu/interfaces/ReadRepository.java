package co.com.biciu.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * This interface defines all the methods required to execute Read operations.
 * @param <T> The class you want to persist instances of.
 * @param <K> The datatype of the unique property that gives identity to every instance of the given class.
 */
public interface ReadRepository<T, K> {
    List<T> findAll();
    Optional<T> findById(K id);
}
