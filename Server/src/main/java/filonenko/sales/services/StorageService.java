package filonenko.sales.services;

import filonenko.sales.dao.StorageDAO;
import filonenko.sales.entities.Sale;
import filonenko.sales.entities.Storage;

import java.util.List;

public class StorageService {

    private static StorageDAO dao = StorageDAO.getInstance();

    public static Storage addStorage(Storage newStorage) {
        dao.create(newStorage);
        System.out.println(newStorage.getId());
        return dao.findById(newStorage.getId()).get();
    }

    public static List<Storage> getStorage() {
        return dao.findAll();
    }

    public static void replenish(Storage storage, int quantity) {
        storage.setQuantity(storage.getQuantity() + quantity);
        dao.update(storage);
    }

    public static boolean useProduct(Sale sale) {
        Storage storage = dao.getByProduct(sale.getProduct());
        if(storage.getQuantity() > sale.getQuantity()) {
            storage.setQuantity(storage.getQuantity() - sale.getQuantity());
            dao.update(storage);
            return true;
        }
        return false;
    }
}
