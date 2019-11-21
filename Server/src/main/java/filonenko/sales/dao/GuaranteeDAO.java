package filonenko.sales.dao;

import filonenko.sales.connect.HibernateConnect;
import filonenko.sales.entities.Guarantee;
import filonenko.sales.entities.Sale;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

//Класс взаимодействия класса-сущности Product и запросов в БД
public class GuaranteeDAO implements DAOInterface<Guarantee> {
    public void deleteByID(Integer id) {
        Guarantee guarantee = findById(id).get();
        delete(guarantee);
    }

    public Guarantee getGuaranteeBySale(Sale sale) {
        Session session = HibernateConnect.getSessionFactory().openSession();   //Получение сессии (возможность обращения к БД)
        Query query = session.createQuery("from Guarantee where sale = :sale "); //Написание строки запроса (выбрать все из таблицы User, где имя = параметру)
        query.setParameter("sale", sale);    //Определение параметра, использованного в запросе
        Guarantee guarantee = (Guarantee)query.getSingleResult();
        session.close();    //Закрытие сесии
        return guarantee;

        }

    private final static class SingletonHolder {    //объект класса единственный для всего проекта
        private final static GuaranteeDAO INSTANCE = new GuaranteeDAO();
    }

    public static GuaranteeDAO getInstance() {   //Получение доступа к классу
        return SingletonHolder.INSTANCE;
    }

    public List<Guarantee> findAll() {   //метод получения всех данных из таблицы
        Session session = HibernateConnect.getSessionFactory().openSession();   //Получение сессии (возможность обращения к БД)
        List<Guarantee> guaranteeData = session.createQuery("from Guarantee").list();  //Получение списка всех данных
        session.close();    //Закрытие сесии
        return guaranteeData;
    }

    public Optional<Guarantee> findById(int id) {    //Получение объекта по его ID
        Session session = HibernateConnect.getSessionFactory().openSession();
        Optional<Guarantee> guaranteeData = Optional.of(session.get(Guarantee.class, id));
        session.close();
        return guaranteeData;
    }

    public void create(Guarantee guaranteeData) { //Создание нового объекта (строки данных в таблице)
        Session session = HibernateConnect.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();   //Открытие транзакции (обхязательно при изменениях в БД)
        session.save(guaranteeData);
        tx1.commit();   //Сохранение состояния и закрытие транзакции
        session.close();
    }

    public void delete(Guarantee guaranteeData) { //Метод удаления строки из таблицы
        Transaction tx = null;
        try (Session session = HibernateConnect.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(guaranteeData);
            tx.commit();
        } catch (HibernateException e) {    //Если такой элемент не найден
            if (tx != null) tx.rollback();  //Откат к состоянию системы до начала транзакии
            e.printStackTrace();
        }
    }

    public void update(Guarantee guaranteeData) { //Изменение строки таблицы
        Session session = HibernateConnect.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(guaranteeData);
        tx1.commit();
        session.close();
    }
}
