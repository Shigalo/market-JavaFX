package filonenko.sales.serverApp;

import filonenko.sales.entities.User;
import filonenko.sales.services.UserService;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ServerThread extends Thread {
    private static int numberOfConnections = 0;
    private static int totalConnections = 0;
    private int ConnectionNumber;

    private BufferedReader inputStream;
    private InetAddress addr;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream; //Поток получения объектов

    ServerThread(Socket socket, InetAddress addr) throws IOException {   //Конструктор, для инициализации
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));    //Входной поток (принимает сообщение от клиента)
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());  //Выходной поток передачи объектов клиенту
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.addr = addr;   //Адресс подключения
        ConnectionNumber = ++totalConnections;

    }

    public void run() { //Начало работы потока
        System.out.println("Connection number " + ConnectionNumber);
        System.out.println("Number of connections " + ++numberOfConnections);
        try {
            while(true) {
                String requestString = inputStream.readLine();
                System.out.println("User " + ConnectionNumber + ") Command: " + requestString);
                switch (requestString) {
//                    case "0":  objectOutputStream.writeObject(UserDAO.getInstance().findAll().get(0)); break; //для теста
                    case "getAllUsers": getAllUsers(); break;
                    case "login": login(); break;
                    case "registration": registration(); break;
                    case "editName": editName();
                }
            }
        } catch (IOException e) {
            System.out.println("Lost connection");} catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally { disconnect(); }
    }

    private void editName() throws IOException, ClassNotFoundException {
        User user = (User)objectInputStream.readObject();
        String newName = inputStream.readLine();
        System.out.println("New name: " + newName);
        user = UserService.editName(user, newName);
        if (user != null) System.out.println("Successful modification");
        else System.out.println("Failed modification");
        objectOutputStream.writeObject(user);
    }

    private void registration() throws IOException, ClassNotFoundException {
        User user = (User)objectInputStream.readObject();
        System.out.println("User login: " + user.getLogin());
        user = UserService.registration(user);
        if (user != null) System.out.println("Successful registration");
        else System.out.println("Failed registration");
        objectOutputStream.writeObject(user);
    }

    private void login() throws IOException, ClassNotFoundException {
        User user = (User)objectInputStream.readObject();
        System.out.println("User login: " + user.getLogin());
        user = UserService.login(user);
        if (user != null) System.out.println("Successful login");
        else System.out.println("Failed login");
        objectOutputStream.writeObject(user);
    }

    private void getAllUsers() throws IOException {
        objectOutputStream.writeObject(UserService.getAllUsers());
    }

    public void disconnect() {
        try {
            System.out.println( "User " + ConnectionNumber + " disconnected: " + addr.getHostName());
            System.out.println("Number of connections " + --numberOfConnections);
            //Закрытие потоков передачи сообщений
            objectOutputStream.close(); //Поток, отправки объектов
            inputStream.close();    //Поток принятия команд
        } catch (IOException e) { e.printStackTrace(); }
        finally { this.interrupt(); }
    }
}