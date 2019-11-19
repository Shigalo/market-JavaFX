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
        List<Status> statusdata = session.createQuery("from Status").list();  //Получение списка всех данных
        session.close();    //Закрытие сесии
        return statusdata;
    }

    public Optional<Status> findById(int id) {    //Получение объекта по его ID
        Session session = HibernateConnect.getSessionFactory().openSession();
        Optional<Status> statusdata = Optional.of(session.get(Status.class, id));
        session.close();
        return statusdata;
    }

    public void create(Status statusdata) { //Создание нового объекта (строки данных в таблице)
        Session session = HibernateConnect.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();   //Открытие транзакции (обхязательно при изменениях в БД)
        session.save(statusdata);
        tx1.commit();   //Сохранение состояния и закрытие транзакции
        session.close();
    }

    public void delete(Status statusdata) { //Метод удаления строки из таблицы
        Transaction tx = null;
        try (Session session = HibernateConnect.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(statusdata);
            tx.commit();
        } catch (HibernateException e) {    //Если такой элемент не найден
            if (tx != null) tx.rollback();  //Откат к состоянию системы до начала транзакии
            e.printStackTrace();
        }
    }

    public void update(Status statusdata) { //Изменение строки таблицы
        Session session = HibernateConnect.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(statusdata);
        tx1.commit();
        session.close();
    }
}
