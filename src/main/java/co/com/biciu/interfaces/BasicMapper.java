package co.com.biciu.interfaces;

/**
 * This interface declares the basic methods a mapper should implement.
 * @param <T> The entity you want to map from/to a dto.
 * @param <K> The dto you want to map from/to an entity.
 */
public interface BasicMapper<T, K> {
    K entityToDTO(T entity);
    T DTOToEntity(K DTO);
}
