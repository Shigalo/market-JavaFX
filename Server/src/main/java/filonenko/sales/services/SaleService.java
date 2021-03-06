package filonenko.sales.services;

import filonenko.sales.dao.DAOInterface;
import filonenko.sales.dao.SaleDAO;
import filonenko.sales.entities.Guarantee;
import filonenko.sales.entities.Product;
import filonenko.sales.entities.Sale;

import java.util.List;

public class SaleService {

    private static DAOInterface<Sale> dao = SaleDAO.getInstance();

    public static List<Sale> getAllSales() {
        return dao.findAll();
    }

    public static void deleteSale(Sale Sale) {
        dao.delete(Sale);
    }

    public static Sale addSale(Sale newSale) {
        if (StorageService.useProduct(newSale)) {
            dao.create(newSale);
            Guarantee guarantee = new Guarantee(newSale.getDate().plusYears(2));
            guarantee.setStatus(StatusService.getAllStatuses().get(0));
            guarantee.setSale(newSale);
            GuaranteeService.addGuarantee(guarantee);
            return dao.findById(newSale.getId()).get();
        }
        return null;
    }

    public static List<Sale> getProductSales(Product product) {
        return SaleDAO.getInstance().getByProduct(product);
    }
}
