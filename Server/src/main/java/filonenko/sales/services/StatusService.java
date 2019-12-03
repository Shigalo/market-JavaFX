package filonenko.sales.services;

import filonenko.sales.dao.DAOInterface;
import filonenko.sales.dao.StatusDAO;
import filonenko.sales.entities.Status;

import java.util.List;

public class StatusService {

    private static DAOInterface<Status> dao = StatusDAO.getInstance();

    public static List<Status> getAllStatuses() {
        return dao.findAll();
    }
}
