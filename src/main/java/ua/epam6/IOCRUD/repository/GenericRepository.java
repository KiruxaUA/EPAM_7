package ua.epam6.IOCRUD.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    void create(T t) throws Exception;
    T getById(ID id) throws Exception;
    void update(T t) throws Exception;
    void delete(ID t) throws Exception;
    List<T> getAll() throws Exception;
}
