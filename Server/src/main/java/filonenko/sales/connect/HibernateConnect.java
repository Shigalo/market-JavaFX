package filonenko.sales.connect;

import filonenko.sales.entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateConnect{  //Класс для получения подключения к БД (паттерн Singleton)

    private static SessionFactory sessionFactory;   //Единственный объект подключения

    private HibernateConnect() {}   //Скрытый конструктор

    public static SessionFactory getSessionFactory() {  //Получения подключения к БД
        if (sessionFactory == null) {   //Если подключение не настроено
            try {
                Configuration configuration = new Configuration().configure();  //Считываения конфигурации (параметров hibernate): файл: resources/hibernate.cfg.xml
//                configuration.setProperty("hibernate.connection.username", "root");   //Ручная настройка параметров подключения
                configuration.addAnnotatedClass(User.class);    //Определение классов - сущностей
                configuration.addAnnotatedClass(Product.class);    //Определение классов - сущностей
                configuration.addAnnotatedClass(Sale.class);    //Определение классов - сущностей
                configuration.addAnnotatedClass(Status.class);    //Определение классов - сущностей
                configuration.addAnnotatedClass(Guarantee.class);    //Определение классов - сущностей
                configuration.addAnnotatedClass(Storage.class);    //Определение классов - сущностей
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()); //Применениие параметров подключения
                sessionFactory = configuration.buildSessionFactory(builder.build());    //Настройка подключения
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;  //Получение подключения
    }

}
