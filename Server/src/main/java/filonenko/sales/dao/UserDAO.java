package filonenko.sales.dao;

import filonenko.sales.entities.User;
import filonenko.sales.connect.HibernateConnect;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

//Класс взаимодействия класса-сущности User и запросов в БД
public class UserDAO implements DAOInterface<User> {

    private final static class SingletonHolder {    //объект класса единственный для всего проекта
        private final static UserDAO INSTANCE = new UserDAO();
    }

    public static UserDAO getInstance() {   //Получение доступа к классу
        return SingletonHolder.INSTANCE;
    }

    public User login(String login, String password) {
        try {
            Session session = HibernateConnect.getSessionFactory().openSession();
            Query query = session.createQuery("from User where login = :login and password = :password "); //Написание строки запроса (выбрать все из таблицы User, где имя = параметру)
            query.setParameter("login", login);    //Определение параметра, использованного в запросе
            query.setParameter("password", password);    //Определение параметра, использованного в запросе
            return (User)query.getSingleResult();    //Выполнение запроса
        } catch (NoResultException e) { return null; }
    }

    public User registration(String login, String password, String name) {
        User newUser = new User(name, login, password, 0);
        create(newUser);
        return newUser;
    }


    public User editName(Integer id, String newName) {
        Session session = HibernateConnect.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        User user = findById(id).get();
        user.setName(newName);
        session.update(user);
        tx1.commit();
        session.close();
        return user;    //Выполнение запроса
    }

    public User editPassword(Integer id, String newPassword) {
        Session session = HibernateConnect.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        User user = findById(id).get();
        user.setPassword(newPassword);
        session.update(user);
        tx1.commit();
        session.close();
        return user;    //Выполнение запроса
    }

    public List<User> findAll() {   //метод получения всех данных из таблицы
        Session session = HibernateConnect.getSessionFactory().openSession();   //Получение сессии (возможность обращения к БД)
        List<User> userdata = session.createQuery("from User").list();  //Получение списка всех данных
        session.close();    //Закрытие сесии
        return userdata;
    }

    public Optional<User> findById(int id) {    //Получение объекта по его ID
        Session session = HibernateConnect.getSessionFactory().openSession();
        Optional<User> userdata = Optional.of(session.get(User.class, id));
        session.close();
        return userdata;
    }

    public void create(User userdata) { //Создание нового объекта (строки данных в таблице)
        Session session = HibernateConnect.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();   //Открытие транзакции (обхязательно при изменениях в БД)
        session.save(userdata);
        tx1.commit();   //Сохранение состояния и закрытие транзакции
        session.close();
    }

    public void delete(User userdata) { //Метод удаления строки из таблицы
        Transaction tx = null;
        try (Session session = HibernateConnect.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(userdata);
            tx.commit();
        } catch (HibernateException e) {    //Если такой элемент не найден
            if (tx != null) tx.rollback();  //Откат к состоянию системы до начала транзакии
            e.printStackTrace();
        }
    }

    public void update(User userdata) { //Изменение строки таблицы
        Session session = HibernateConnect.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(userdata);
        tx1.commit();
        session.close();
    }

    public List<User> findByName(String new_name) {  //Поиск записи по имени
        Session session = HibernateConnect.getSessionFactory().openSession();
        Query query = session.createQuery("from User where name = :name "); //Написание строки запроса (выбрать все из таблицы User, где имя = параметру)
        query.setParameter("name", new_name);    //Определение параметра, использованного в запросе
        return query.list();    //Выполнение запроса
    }

    public User findByLogin(String login) {  //Поиск записи по имени
        try {
            Session session = HibernateConnect.getSessionFactory().openSession();
            Query query = session.createQuery("from User where login = :login "); //Написание строки запроса (выбрать все из таблицы User, где имя = параметру)
            query.setParameter("login", login);    //Определение параметра, использованного в запросе
            return (User) query.getSingleResult();    //Выполнение запроса
        } catch (NoResultException e) { return null; }
    }

    public void deleteByName(String name) { //Удаление по имени
        Transaction tx = null;
        try (Session session = HibernateConnect.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Query query = session.createQuery("delete User where name = :name ");
            query.setParameter("name", name);
            query.executeUpdate();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

}
