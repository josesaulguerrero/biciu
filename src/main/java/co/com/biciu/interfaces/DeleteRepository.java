package co.com.biciu.interfaces;

/**
 * This interface defines all the methods required to execute Delete operations.
 * @param <K> The datatype of the unique property that gives identity to every instance of the given class.
 */
public interface DeleteRepository<K> {
    Boolean delete(K id);
}
