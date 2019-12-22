package ua.epam6.IOCRUD.repository;

import ua.epam6.IOCRUD.exceptions.ChangesRejectedException;
import ua.epam6.IOCRUD.exceptions.NoSuchElementException;

import java.util.List;

public interface GenericRepository<T, ID> {
    T create(T t) throws ChangesRejectedException, NoSuchElementException;
    T getByID(ID id) throws NoSuchElementException;
    T update(T t) throws NoSuchElementException, ChangesRejectedException;
    void delete(T t) throws NoSuchElementException, ChangesRejectedException;
    List<T> getAll() throws NoSuchElementException, ChangesRejectedException;
}
