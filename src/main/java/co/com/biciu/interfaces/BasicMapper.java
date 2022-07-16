package co.com.biciu.interfaces;

import org.mapstruct.Mapper;

//@Mapper()
public interface BasicMapper<T, K> {
    K entityToDTO(T entity);
    T DTOToEntity(K DTO);
}
