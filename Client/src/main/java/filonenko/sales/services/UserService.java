package filonenko.sales.services;

import filonenko.sales.app.Connection;
import filonenko.sales.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

@Getter @Setter
public class UserService {

    private static List<User> userList;

    public static List<User> getAllUsers() throws IOException, ClassNotFoundException {
        return Connection.getInstance().getUserList();
    }
}
