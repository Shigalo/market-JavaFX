package filonenko.sales.services;

import filonenko.sales.dao.SaleDAO;
import filonenko.sales.entities.Sale;

import java.util.ArrayList;

public class SaleService {

    private static ArrayList<Sale> saleList;
    private static SaleDAO dao = SaleDAO.getInstance();

    public static ArrayList<Sale> getAllSales() {
        saleList = new ArrayList<>(dao.findAll());
        return saleList;
    }

    /*public static Sale editSale(Sale Sale, String newName, String newFirm) {
        return dao.editSale(Sale.getId(), newName, newFirm);
    }*/

    public static void deleteSale(Sale Sale) {
        dao.delete(Sale);
    }

    public static Sale addSale(Sale newSale) {
        dao.create(newSale);
        System.out.println(newSale.getId());
        return dao.findById(newSale.getId()).get();
    }
}
