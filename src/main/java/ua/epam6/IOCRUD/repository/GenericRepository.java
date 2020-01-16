package ua.epam6.IOCRUD.repository;

import ua.epam6.IOCRUD.exceptions.NoSuchElementException;

import java.util.List;

public interface GenericRepository<T, ID> {
    T create(T t) throws NoSuchElementException;
    T getById(ID id);
    T update(T t) throws NoSuchElementException;
    void delete(T t) throws NoSuchElementException;
    List<T> getAll();
}
