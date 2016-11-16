package bussiness;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mjovel on 11-15-16.
 */
public interface Crud <T, K extends Serializable> {
    void persist(T object);
    List<T> findAll();
    List<T> findAll(String... orderBy);
    List<T> findAllPaged(int first, int size, String... orderBy);
    T get(K id);
    void delete(T object);
    void delete(K key);
}
