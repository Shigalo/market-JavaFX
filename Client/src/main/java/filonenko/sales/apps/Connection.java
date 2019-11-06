package filonenko.sales.apps;

import filonenko.sales.entities.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class Connection {

    private static Connection instance = null;

    public static Connection getInstance() {
        if(instance == null) {
            instance = new Connection();
        }
        return instance;
    }

    private PrintStream printStream; //Поток отправки команды
    private ObjectInputStream objectInputStream; //Поток получения объектов
    private ObjectOutputStream objectOutputStream;

    private Connection() {
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), 8071);
            printStream = new PrintStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            /*printStream.println("0");
            User user = (User)objectInputStream.readObject();
            System.out.println(user);*/

            /*printStream.println("getAllUsers");
            UserService.setUserList((ArrayList<User>)objectInputStream.readObject());
            for(User buf : UserService.getAllUsers()) { System.out.println(buf); }*/
        } catch (Exception e) { e.printStackTrace(); }
    }
    public List<User> getUserList() throws IOException, ClassNotFoundException {
        printStream.println("getAllUsers");
        return (List<User>)objectInputStream.readObject();
    }

    public User login(User user) throws IOException, ClassNotFoundException {
        printStream.println("login");
        objectOutputStream.writeObject(user);
        return (User)objectInputStream.readObject();
    }

    public User registration(User user) throws IOException, ClassNotFoundException {
        printStream.println("registration");
        objectOutputStream.writeObject(user);
        return (User)objectInputStream.readObject();
    }

    public User editName(User user, String newName) throws IOException, ClassNotFoundException {
        printStream.println("editName");
        objectOutputStream.writeObject(user);
        printStream.println(newName);
        return (User)objectInputStream.readObject();
    }
}
