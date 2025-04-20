package util;

import java.util.List;

public interface DatabaseOperation<T> {
    void save(T t);
    String update(T t);
    List<T> findAll();
    T findById(int id);
}
