package filonenko.sales.serverApp;

import filonenko.sales.entities.*;
import filonenko.sales.services.*;

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
                    case "editPassword": editPassword();
                    case "getAllProducts": getAllProducts(); break;
                    case "editProduct": editProduct(); break;
                    case "deleteProduct": deleteProduct(); break;
                    case "addProduct": addProduct(); break;
                    case "remove": remove(); break;
                    case "getAllSales": getAllSales(); break;
                    case "addSale": addSale(); break;
                    case "deleteSale": deleteSale(); break;
                }
            }
        } catch (IOException e) {
            System.out.println("Lost connection");} catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally { disconnect(); }
    }

    private void deleteSale() throws IOException, ClassNotFoundException {
        Sale sale = (Sale)objectInputStream.readObject();
        System.out.println(sale);
        SaleService.deleteSale(sale);
        System.out.println("Successful delete");
        System.out.println("Failed delete");
    }
    private void addSale() throws IOException, ClassNotFoundException {
        Sale newSale = (Sale)objectInputStream.readObject();
        newSale = SaleService.addSale(newSale);
        if (newSale != null) System.out.println("successful addition ");
        else System.out.println("Failed addition");
        objectOutputStream.writeObject(newSale);
    }
    private void getAllSales() throws IOException {
        objectOutputStream.writeObject(SaleService.getAllSales());
    }

    private void addProduct() throws IOException, ClassNotFoundException {
        Product newProduct = (Product)objectInputStream.readObject();
        newProduct = ProductService.addProduct(newProduct);
        if (newProduct != null) System.out.println("successful addition ");
        else System.out.println("Failed addition");
        objectOutputStream.writeObject(newProduct);
    }
    private void deleteProduct() throws IOException, ClassNotFoundException {
        Product product = (Product)objectInputStream.readObject();
        System.out.println(product);
        ProductService.deleteProduct(product);
        System.out.println("Successful delete");
        System.out.println("Failed delete");
    }
    private void editProduct() throws IOException, ClassNotFoundException {
        Product product = (Product)objectInputStream.readObject();
        String newName = inputStream.readLine();
        System.out.println("New name: " + newName);
        String newFirm = inputStream.readLine();
        System.out.println("New firm: " + newFirm);
        Double newCost = Double.parseDouble(inputStream.readLine());
        System.out.println("New cost: " + newCost);
        product = ProductService.editProduct(product, newName, newFirm, newCost);
        if (product != null) System.out.println("Successful modification");
        else System.out.println("Failed modification");
        objectOutputStream.writeObject(product);
    }
    private void getAllProducts() throws IOException {
        objectOutputStream.writeObject(ProductService.getAllProducts());
    }

    private void editPassword() throws IOException, ClassNotFoundException {
        User user = (User)objectInputStream.readObject();
        String newPassword = inputStream.readLine();
        System.out.println("New password: " + newPassword);
        user = UserService.editPassword(user, newPassword);
        if (user != null) System.out.println("Successful modification");
        else System.out.println("Failed modification");
        objectOutputStream.writeObject(user);
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
    private void remove() throws IOException, ClassNotFoundException {
        User user = (User)objectInputStream.readObject();
        System.out.println(user);
        UserService.remove(user);
        System.out.println(user + " was removed");
    }
    private void disconnect() {
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