package filonenko.sales.services;

import filonenko.sales.dao.GuaranteeDAO;
import filonenko.sales.entities.Guarantee;
import filonenko.sales.entities.Sale;

import java.util.ArrayList;

public class GuaranteeService {

    private static ArrayList<Guarantee> guaranteeList;
    private static GuaranteeDAO dao = GuaranteeDAO.getInstance();

    public static ArrayList<Guarantee> getAllGuarantees() {
        guaranteeList = new ArrayList<>(dao.findAll());
        return guaranteeList;
    }

  /*  public static Guarantee editGuarantee(Guarantee Guarantee, String newName, String newFirm, Double newCost) {
        return dao.editGuarantee(Guarantee.getId(), newName, newFirm, newCost);
    }*/

    public static void deleteGuarantee(Guarantee guarantee) {
        dao.delete(guarantee);
    }

    public static Guarantee addGuarantee(Guarantee newGuarantee) {
        dao.create(newGuarantee);
        System.out.println(newGuarantee.getId());
        return dao.findById(newGuarantee.getId()).get();
    }

    public static Guarantee getGuarantee(Sale sale) {
        return dao.getGuaranteeBySale(sale);
    }
}
