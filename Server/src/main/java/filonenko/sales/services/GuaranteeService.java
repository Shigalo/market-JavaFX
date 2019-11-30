package filonenko.sales.services;

import filonenko.sales.dao.GuaranteeDAO;
import filonenko.sales.entities.Guarantee;
import filonenko.sales.entities.Sale;

import java.time.LocalDate;
import java.util.List;

public class GuaranteeService {

    private static GuaranteeDAO dao = GuaranteeDAO.getInstance();

    public static List<Guarantee> getAllGuarantees() {
        return dao.findAll();
    }

  /*  public static Guarantee editGuarantee(Guarantee Guarantee, String newName, String newFirm, Double newCost) {
        return dao.editGuarantee(Guarantee.getId(), newName, newFirm, newCost);
    }*/

    public static Guarantee addGuarantee(Guarantee newGuarantee) {
        dao.create(newGuarantee);
        System.out.println(newGuarantee.getId());
        return dao.findById(newGuarantee.getId()).get();
    }

    public static Guarantee getGuarantee(Sale sale) {
        return dao.getGuaranteeBySale(sale);
    }

    public static void update() {
        LocalDate today = LocalDate.now();
        List<Guarantee> guaranteeList = dao.findAll();
        for(Guarantee guarantee : guaranteeList) {
            if(guarantee.getStatus().getId() == 1 && guarantee.getDate().isBefore(today)) {
                guarantee.getStatus().setId(2);
                dao.update(guarantee);
            }
        }
    }

    public static Guarantee guaranteeUpdate(Guarantee guarantee, String status) {
        guarantee.setStatus(StatusService.getAllStatuses().get(Integer.valueOf(status)));
        guarantee.setDate(LocalDate.now());
        dao.update(guarantee);
        SaleService.addSale(guarantee.getSale());
        return guarantee;
    }
}
