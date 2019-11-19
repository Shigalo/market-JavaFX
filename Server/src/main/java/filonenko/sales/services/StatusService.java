package filonenko.sales.services;

import filonenko.sales.dao.StatusDAO;
import filonenko.sales.entities.Status;

import java.util.ArrayList;

public class StatusService {

    private static ArrayList<Status> statusList;
    private static StatusDAO dao = StatusDAO.getInstance();

    public static ArrayList<Status> getAllStatuses() {
        statusList = new ArrayList<>(dao.findAll());
        return statusList;
    }

    /*public static Product editProduct(Product product, String newName, String newFirm, Double newCost) {
        return dao.editProduct(product.getId(), newName, newFirm, newCost);
    }*/

    public static void deleteStatus(Status status) {
        dao.delete(status);
    }

    public static Status addStatus(Status newStatus) {
        dao.create(newStatus);
        System.out.println(newStatus.getId());
        return dao.findById(newStatus.getId()).get();
    }
}
