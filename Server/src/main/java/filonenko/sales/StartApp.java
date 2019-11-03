package filonenko.sales;

import filonenko.sales.serverApp.Server;

public class StartApp {

    public static void main(String[] args) throws Exception { //точка входа в приложние
        System.out.println("Deploy server");
        new Server().start();
    }
}
