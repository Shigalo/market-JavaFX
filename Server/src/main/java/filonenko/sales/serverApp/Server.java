package filonenko.sales.serverApp;

import filonenko.sales.dao.UserDAO;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void start() throws IOException {
        ServerSocket port = new ServerSocket(8071);    //Открытие сервера на порте 8071 (прослушивание подключений)
        System.out.println("DB connection setup");
        UserDAO.getInstance().findAll();
        System.out.println("DB connection successful");
        System.out.println("Server start up\nAddress: " + port.getInetAddress().getHostAddress() + ":" + port.getLocalPort());
        while (true) {  //"Бесконечное прослушивание" (пока не закроется приложение)
            Socket socket = port.accept();   //Если было новое подключение

            InetAddress address = socket.getInetAddress();   //Получение адреса работы сервера
            ServerThread thread = new ServerThread(socket, address);    //Открытие нового потока работы с подключение (клиентом)
            System.out.println("Новое подключение\nАдресс: " + address.getHostName());
            thread.start(); //Запуск сервера (вызов метода run)
        }
    }
}
