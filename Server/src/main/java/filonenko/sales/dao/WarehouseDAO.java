package filonenko.sales.dao;

import filonenko.sales.connect.HibernateConnect;
import filonenko.sales.entities.Warehouse;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

//Класс взаимодействия класса-сущности Product и запросов в БД
public class WarehouseDAO implements DAOInterface<Warehouse> {

    private final static class SingletonHolder {    //объект класса единственный для всего проекта
        private final static WarehouseDAO INSTANCE = new WarehouseDAO();
    }

    public static WarehouseDAO getInstance() {   //Получение доступа к классу
        return SingletonHolder.INSTANCE;
    }

    public List<Warehouse> findAll() {   //метод получения всех данных из таблицы
        Session session = HibernateConnect.getSessionFactory().openSession();   //Получение сессии (возможность обращения к БД)
        List<Warehouse> warehousedata = session.createQuery("from Warehouse").list();  //Получение списка всех данных
        session.close();    //Закрытие сесии
        return warehousedata;
    }

    public Optional<Warehouse> findById(int id) {    //Получение объекта по его ID
        Session session = HibernateConnect.getSessionFactory().openSession();
        Optional<Warehouse> warehousedata = Optional.of(session.get(Warehouse.class, id));
        session.close();
        return warehousedata;
    }

    public void create(Warehouse warehousedata) { //Создание нового объекта (строки данных в таблице)
        Session session = HibernateConnect.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();   //Открытие транзакции (обхязательно при изменениях в БД)
        session.save(warehousedata);
        tx1.commit();   //Сохранение состояния и закрытие транзакции
        session.close();
    }

    public void delete(Warehouse warehousedata) { //Метод удаления строки из таблицы
        Transaction tx = null;
        try (Session session = HibernateConnect.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(warehousedata);
            tx.commit();
        } catch (HibernateException e) {    //Если такой элемент не найден
            if (tx != null) tx.rollback();  //Откат к состоянию системы до начала транзакии
            e.printStackTrace();
        }
    }

    public void update(Warehouse warehousedata) { //Изменение строки таблицы
        Session session = HibernateConnect.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(warehousedata);
        tx1.commit();
        session.close();
    }
}
