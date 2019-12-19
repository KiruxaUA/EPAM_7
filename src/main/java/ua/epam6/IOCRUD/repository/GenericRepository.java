package ua.epam6.IOCRUD.repository;

import ua.epam6.IOCRUD.exceptions.ChangesRejectedException;
import ua.epam6.IOCRUD.exceptions.NoSuchElementException;
import ua.epam6.IOCRUD.model.GenericObject;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T extends GenericObject, ID> {
    void create(T t) throws ChangesRejectedException, NoSuchElementException;
    T readByID(ID id) throws NoSuchElementException;
    void update(T t) throws NoSuchElementException, ChangesRejectedException;
    void delete(T t) throws NoSuchElementException, ChangesRejectedException;
    List<T> readAll() throws NoSuchElementException, ChangesRejectedException;

    default Long getLastId() throws NoSuchElementException, ChangesRejectedException {
        List<T> developers = readAll();
        if (developers.size() == 0) {
            return 0L;
        }
        Optional<Long> optional = developers.stream().map(T::getId).reduce((a, b) -> a.compareTo(b) >= 0 ? a : b);
        return optional.orElse(null);
    }
}
