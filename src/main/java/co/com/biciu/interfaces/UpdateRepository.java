package co.com.biciu.interfaces;

/**
 * This interface defines all the methods required to execute Update operations.
 * @param <T> The class you want to persist instances of.
 * @param <K> The datatype of the unique property that gives identity to every instance of the given class.
 */
public interface UpdateRepository<T, K> {
    <S extends T> T update(K id, S updatedObject);
}
