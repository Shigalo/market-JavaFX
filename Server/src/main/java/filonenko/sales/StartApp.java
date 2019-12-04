package filonenko.sales;

import filonenko.sales.connect.HibernateConnect;
import filonenko.sales.serverApp.Server;

public class StartApp {

    public static void main(String[] args) throws Exception { //точка входа в приложние
        System.out.println("Deploy server");
        switch (args.length) {
            case 3: HibernateConnect.password = args[2];
            case 2: HibernateConnect.user = args[1];
            case 1: HibernateConnect.name = args[0];
        }
        new Server().start();
    }
}
