package lk.sliit.parkingmanagement.oopapp.dao;

import java.util.List;

public interface DAO<T> {
    T getById(int id) throws Exception;
    List<T> findAll() throws Exception;
    void create(T object) throws Exception;
    void update(T object) throws Exception;
    void delete(int id) throws Exception;
}
