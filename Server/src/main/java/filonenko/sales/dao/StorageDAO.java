package filonenko.sales.dao;

import filonenko.sales.connect.HibernateConnect;
import filonenko.sales.entities.Storage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

//Класс взаимодействия класса-сущности Product и запросов в БД
public class StorageDAO implements DAOInterface<Storage> {

    private final static class SingletonHolder {    //объект класса единственный для всего проекта
        private final static StorageDAO INSTANCE = new StorageDAO();
    }

    public static StorageDAO getInstance() {   //Получение доступа к классу
        return SingletonHolder.INSTANCE;
    }

    public List<Storage> findAll() {   //метод получения всех данных из таблицы
        Session session = HibernateConnect.getSessionFactory().openSession();   //Получение сессии (возможность обращения к БД)
        List<Storage> storageData = session.createQuery("from Storage").list();  //Получение списка всех данных
        session.close();    //Закрытие сесии
        return storageData;
    }

    public Optional<Storage> findById(int id) {    //Получение объекта по его ID
        Session session = HibernateConnect.getSessionFactory().openSession();
        Optional<Storage> storageData = Optional.of(session.get(Storage.class, id));
        session.close();
        return storageData;
    }

    public void create(Storage storageData) { //Создание нового объекта (строки данных в таблице)
        Session session = HibernateConnect.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();   //Открытие транзакции (обхязательно при изменениях в БД)
        session.save(storageData);
        tx1.commit();   //Сохранение состояния и закрытие транзакции
        session.close();
    }

    public void delete(Storage storageData) { //Метод удаления строки из таблицы
        Transaction tx = null;
        try (Session session = HibernateConnect.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(storageData);
            tx.commit();
        } catch (HibernateException e) {    //Если такой элемент не найден
            if (tx != null) tx.rollback();  //Откат к состоянию системы до начала транзакии
            e.printStackTrace();
        }
    }

    public void update(Storage storageData) { //Изменение строки таблицы
        Session session = HibernateConnect.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(storageData);
        tx1.commit();
        session.close();
    }
}
