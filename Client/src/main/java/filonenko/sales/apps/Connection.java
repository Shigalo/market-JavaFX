package filonenko.sales.apps;

import filonenko.sales.entities.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class Connection {

    private static Connection instance = null;

    public static Connection getInstance() {
        if(instance == null) { instance = new Connection(); }
        return instance;
    }

    private ObjectInputStream objectInputStream; //Поток получения объектов
    private ObjectOutputStream objectOutputStream;

    private Connection() {
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), 8071);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<User> getUserList() throws IOException, ClassNotFoundException {
        System.out.println("getUserList");
        objectOutputStream.writeObject("getAllUsers");
        return (List<User>)objectInputStream.readObject();
    }
    public User login(User user) {
        try {
            objectOutputStream.writeObject("login");
            objectOutputStream.writeObject(user);
            user = (User) objectInputStream.readObject();
            System.out.println(user);
            return user;
        } catch (Exception e) { e.printStackTrace(); return null; }
    }
    public User registration(User user) throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject("registration");
        objectOutputStream.writeObject(user);
        return (User)objectInputStream.readObject();
    }
    public User editName(User user, String newName) throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject("editName");
        objectOutputStream.writeObject(user);
        objectOutputStream.writeObject(newName);
        return (User)objectInputStream.readObject();
    }
    public User editPassword(User user, String newPassword) throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject("editPassword");
        objectOutputStream.writeObject(user);
        objectOutputStream.writeObject(newPassword);
        return (User)objectInputStream.readObject();
    }
    public void remove(User user) throws IOException {
        objectOutputStream.writeObject("remove");
        objectOutputStream.writeObject(user);
    }

    public List<Product> getProductList() throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject("getAllProducts");
        return (List<Product>)objectInputStream.readObject();
    }
    public Product editProduct(Product selectedProduct, String name, String firm, String cost) throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject("editProduct");
        System.out.println(selectedProduct);
        objectOutputStream.writeObject(selectedProduct);
        System.out.println(name);
        objectOutputStream.writeObject(name);
        System.out.println(firm);
        objectOutputStream.writeObject(firm);
        System.out.println(cost);
        objectOutputStream.writeObject(cost);
        System.out.println(4);
        selectedProduct = (Product)objectInputStream.readObject();
        return selectedProduct;
    }
    public void deleteProduct(Product selectedProduct) throws IOException {
        objectOutputStream.writeObject("deleteProduct");
        objectOutputStream.writeObject(selectedProduct);
    }
    public Product addProduct(Product product) throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject("addProduct");
        objectOutputStream.writeObject(product);
        return (Product)objectInputStream.readObject();
    }

    public List<Sale> getSalesList() throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject("getAllSales");
        return (List<Sale>)objectInputStream.readObject();
    }
    public void addSale(Sale sale) throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject("addSale");
        System.out.println(sale);
        objectOutputStream.writeObject(sale);
        objectInputStream.readObject();
    }
    public void deleteSale(Sale selectedSale) throws IOException {
        objectOutputStream.writeObject("deleteSale");
        objectOutputStream.writeObject(selectedSale);
    }

    public List<Storage> getStorage() throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject("getStorage");
        return (List<Storage>)objectInputStream.readObject();
    }

    public void replenish(Storage storage, String quantity) throws IOException {
        objectOutputStream.writeObject("replenish");
        System.out.println(storage);
        objectOutputStream.writeObject(storage);
        objectOutputStream.writeObject(quantity);
    }

    public Guarantee getGuarantee(Sale sale) throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject("getGuarantee");
        System.out.println(sale);
        objectOutputStream.writeObject(sale);
        return (Guarantee)objectInputStream.readObject();
    }
}
