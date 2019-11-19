package filonenko.sales.services;

import filonenko.sales.dao.WarehouseDAO;
import filonenko.sales.entities.Warehouse;

import java.util.ArrayList;

public class WarehouseService {

    private static ArrayList<Warehouse> warehouseList;
    private static WarehouseDAO dao = WarehouseDAO.getInstance();

    public static ArrayList<Warehouse> getAllWarehousees() {
        warehouseList = new ArrayList<>(dao.findAll());
        return warehouseList;
    }

    /*public static Product editProduct(Product product, String newName, String newFirm, Double newCost) {
        return dao.editProduct(product.getId(), newName, newFirm, newCost);
    }*/

    public static void deleteWarehouse(Warehouse warehouse) {
        dao.delete(warehouse);
    }

    public static Warehouse addWarehouse(Warehouse newWarehouse) {
        dao.create(newWarehouse);
        System.out.println(newWarehouse.getId());
        return dao.findById(newWarehouse.getId()).get();
    }
}
