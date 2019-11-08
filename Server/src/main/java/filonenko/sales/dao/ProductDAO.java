package filonenko.sales.dao;

import filonenko.sales.connect.HibernateConnect;
import filonenko.sales.entities.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

//Класс взаимодействия класса-сущности Product и запросов в БД
public class ProductDAO implements DAOInterface<Product> {

    private final static class SingletonHolder {    //объект класса единственный для всего проекта
        private final static ProductDAO INSTANCE = new ProductDAO();
    }

    public static ProductDAO getInstance() {   //Получение доступа к классу
        return SingletonHolder.INSTANCE;
    }

    public List<Product> findByName(String new_name) {  //Поиск записи по имени
        Session session = HibernateConnect.getSessionFactory().openSession();
        Query query = session.createQuery("from Product where name = :name "); //Написание строки запроса (выбрать все из таблицы User, где имя = параметру)
        query.setParameter("name", new_name);    //Определение параметра, использованного в запросе
        return query.list();    //Выполнение запроса
    }

    public void deleteByName(String name) { //Удаление по имени
        Transaction tx = null;
        try (Session session = HibernateConnect.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Query query = session.createQuery("delete Product where name = :name ");
            query.setParameter("name", name);
            query.executeUpdate();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public List<Product> findAll() {   //метод получения всех данных из таблицы
        Session session = HibernateConnect.getSessionFactory().openSession();   //Получение сессии (возможность обращения к БД)
        List<Product> productdata = session.createQuery("from Product").list();  //Получение списка всех данных
        session.close();    //Закрытие сесии
        return productdata;
    }

    public Optional<Product> findById(int id) {    //Получение объекта по его ID
        Session session = HibernateConnect.getSessionFactory().openSession();
        Optional<Product> productdata = Optional.of(session.get(Product.class, id));
        session.close();
        return productdata;
    }

    public void create(Product productdata) { //Создание нового объекта (строки данных в таблице)
        Session session = HibernateConnect.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();   //Открытие транзакции (обхязательно при изменениях в БД)
        session.save(productdata);
        tx1.commit();   //Сохранение состояния и закрытие транзакции
        session.close();
    }

    public void delete(Product productdata) { //Метод удаления строки из таблицы
        Transaction tx = null;
        try (Session session = HibernateConnect.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(productdata);
            tx.commit();
        } catch (HibernateException e) {    //Если такой элемент не найден
            if (tx != null) tx.rollback();  //Откат к состоянию системы до начала транзакии
            e.printStackTrace();
        }
    }

    public void update(Product productdata) { //Изменение строки таблицы
        Session session = HibernateConnect.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(productdata);
        tx1.commit();
        session.close();
    }
}
