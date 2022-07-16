package co.com.biciu.interfaces;

/**
 * This interface defines all the methods required to execute Create operations.
 * @param <T> The class you want to persist instances of.
 */
public interface CreateRepository<T> {
    <S extends T> T save(S object);
}
