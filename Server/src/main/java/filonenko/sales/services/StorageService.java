package filonenko.sales.services;

import filonenko.sales.dao.StorageDAO;
import filonenko.sales.entities.Storage;

import java.util.ArrayList;

public class StorageService {

    private static ArrayList<Storage> storageList;
    private static StorageDAO dao = StorageDAO.getInstance();

    public static void deleteStorage(Storage storage) {
        dao.delete(storage);
    }

    public static Storage addStorage(Storage newStorage) {
        dao.create(newStorage);
        System.out.println(newStorage.getId());
        return dao.findById(newStorage.getId()).get();
    }

    public static ArrayList<Storage> getStorage() {
        storageList = new ArrayList<>(dao.findAll());
        return storageList;
    }

    public static void replenish(Storage storage, int quantity) {
        storage.setQuantity(storage.getQuantity() + quantity);
        dao.update(storage);
    }
}
