package filonenko.sales.dao;

import java.util.List;
import java.util.Optional;

//Интерфейс, для работы классов - сущностей и БД (список требуемых методов)
public interface DAOInterface<T> {
    List<T> findAll();

    Optional<T> findById(int id);

    void create(T entity);

    void delete(T entity);

    void update(T entity);
}
