package filonenko.sales.dao;

import filonenko.sales.connect.HibernateConnect;
import filonenko.sales.entities.Status;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

//Класс взаимодействия класса-сущности Product и запросов в БД
public class StatusDAO implements DAOInterface<Status> {

    public void deleteByName(String name) {
        Transaction tx = null;
        try (Session session = HibernateConnect.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Query query = session.createQuery("delete Status where name = :name ");
            query.setParameter("name", name);
            query.executeUpdate();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    private final static class SingletonHolder {    //объект класса единственный для всего проекта
        private final static StatusDAO INSTANCE = new StatusDAO();
    }

    public static StatusDAO getInstance() {   //Получение доступа к классу
        return SingletonHolder.INSTANCE;
    }

    public List<Status> findAll() {   //метод получения всех данных из таблицы
        Session session = HibernateConnect.getSessionFactory().openSession();   //Получение сессии (возможность обращения к БД)
        List<Status> statusData = session.createQuery("from Status").list();  //Получение списка всех данных
        session.close();    //Закрытие сесии
        return statusData;
    }

    public Optional<Status> findById(int id) {    //Получение объекта по его ID
        Session session = HibernateConnect.getSessionFactory().openSession();
        Optional<Status> statusData = Optional.of(session.get(Status.class, id));
        session.close();
        return statusData;
    }

    public void create(Status statusData) { //Создание нового объекта (строки данных в таблице)
        Session session = HibernateConnect.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();   //Открытие транзакции (обхязательно при изменениях в БД)
        session.save(statusData);
        tx1.commit();   //Сохранение состояния и закрытие транзакции
        session.close();
    }

    public void delete(Status statusData) { //Метод удаления строки из таблицы
        Transaction tx = null;
        try (Session session = HibernateConnect.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(statusData);
            tx.commit();
        } catch (HibernateException e) {    //Если такой элемент не найден
            if (tx != null) tx.rollback();  //Откат к состоянию системы до начала транзакии
            e.printStackTrace();
        }
    }

    public void update(Status statusData) { //Изменение строки таблицы
        Session session = HibernateConnect.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(statusData);
        tx1.commit();
        session.close();
    }
}
