package filonenko.sales.connect;

import filonenko.sales.entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateConnect{  //Класс для получения подключения к БД (паттерн Singleton)

    private static SessionFactory sessionFactory;   //Единственный объект подключения

    private HibernateConnect() {}   //Скрытый конструктор

    public static String name = null, user = null, password = null;

    private static void getParam(Configuration configuration) {
        if(name == null) name = "filonenko5";
        if(user == null)  user = "root";
        if(password == null)  password = "root";

        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/"+name+"?verifyServerCertificate=false" +
                "&useSSL=false" +
                "&requireSSL=false" +
                "&useLegacyDatetimeCode=false" +
                "&amp" +
                "&serverTimezone=UTC");
        configuration.setProperty("hibernate.connection.username", user);
        configuration.setProperty("hibernate.connection.password", password);
    }

    public static SessionFactory getSessionFactory() {  //Получения подключения к БД
        if (sessionFactory == null) {   //Если подключение не настроено
            try {
                Configuration configuration = new Configuration().configure();  //Считываения конфигурации (параметров hibernate): файл: resources/hibernate.cfg.xml
                getParam(configuration);
                configuration.addAnnotatedClass(User.class);    //Определение классов - сущностей
                configuration.addAnnotatedClass(Product.class);    //Определение классов - сущностей
                configuration.addAnnotatedClass(Sale.class);    //Определение классов - сущностей
                configuration.addAnnotatedClass(Status.class);    //Определение классов - сущностей
                configuration.addAnnotatedClass(Guarantee.class);    //Определение классов - сущностей
                configuration.addAnnotatedClass(Storage.class);    //Определение классов - сущностей
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()); //Применениие параметров подключения
                sessionFactory = configuration.buildSessionFactory(builder.build());    //Настройка подключения
            } catch (Exception e) {
                System.out.println("Cannot connect to DB");
                e.printStackTrace();
            }
        }
        return sessionFactory;  //Получение подключения
    }

}
