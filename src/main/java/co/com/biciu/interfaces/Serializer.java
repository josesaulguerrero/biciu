package co.com.biciu.interfaces;

public interface Serializer<T, S> {
    S serialize(T entity);
    T deserialize(S serializedObject);
}
