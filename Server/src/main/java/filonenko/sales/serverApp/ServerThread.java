package filonenko.sales.serverApp;

import filonenko.sales.dao.UserDAO;
import filonenko.sales.services.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ServerThread extends Thread {
    private static int numberOfConnections = 0;
    private static int totalConnections = 0;
    private int ConnectionNumber;


    //    private PrintStream outputStream;
    private BufferedReader inputStream;
    private InetAddress addr;
    static String RequestString;
    //    static String AnswerString;
    private ObjectOutputStream objectOutputStream;


    ServerThread(Socket socket, InetAddress addr) throws IOException {   //Конструктор, для инициализации
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));    //Входной поток (принимает сообщение от клиента)
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());  //Выходной поток передачи объектов клиенту
        this.addr = addr;   //Адресс подключения
        ConnectionNumber = ++totalConnections;

    }

    public void run() { //Начало работы потока
        System.out.println("Номер подключения " + ConnectionNumber);
        System.out.println("Количество подключений " + ++numberOfConnections);
        try {
            while(true) {
                RequestString = inputStream.readLine();
                System.out.println("User " + ConnectionNumber + ") Command: " + RequestString);
                switch (RequestString) {
                    case "0":  objectOutputStream.writeObject(UserDAO.getInstance().findAll().get(0)); break;
                    case "getAllUsers": objectOutputStream.writeObject(UserService.getAllUsers()); break;
                }
            }
        } catch (IOException e) {
            System.out.println("Связь потеряна");}
        finally { disconnect(); }
    }

    public void disconnect() {
        try {
            System.out.println( "Пользователь " + ConnectionNumber + " отключился: " + addr.getHostName());
            System.out.println("Количество подключений " + --numberOfConnections);
            //Закрытие потоков передачи сообщений
            objectOutputStream.close(); //Поток, отправки объектов
            inputStream.close();    //Поток принятия команд
        } catch (IOException e) { e.printStackTrace(); }
        finally { this.interrupt(); }
    }
}