package co.com.biciu.interfaces;

public interface BasicMapper<T, K> {
    K entityToDTO(T entity);
    T DTOToEntity(K DTO);
}
