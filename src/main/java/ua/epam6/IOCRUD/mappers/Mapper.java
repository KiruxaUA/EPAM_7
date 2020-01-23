package ua.epam6.IOCRUD.mappers;

public interface Mapper<T, S, ID> {
    T map(S source, ID searchID) throws Exception;
}
