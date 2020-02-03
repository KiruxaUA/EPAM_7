package ua.epam6.IOCRUD.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    T create(T t) throws Exception;
    T getById(ID id) throws Exception;
    T update(T t) throws Exception;
    void delete(ID t) throws Exception;
    List<T> getAll() throws Exception;
}
