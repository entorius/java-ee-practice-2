package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Customer;
import lt.vu.entities.TableEntity;
import lt.vu.persistence.CustomerDAO;
import lt.vu.persistence.TableEntityDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Model
public class Customers {
    @Inject
    private TableEntityDAO tableEntityDAO;

    @Inject
    private CustomerDAO customerDAO;

    @Getter
    @Setter
    private Integer selectedCustomerId;

    @Getter
    @Setter
    private Date customerDate;

    @Getter
    @Setter
    private Customer customerToCreate = new Customer();

    @Getter
    private List<Customer> allCustomers;


    @Getter
    private TableEntity tableEntity;

    @PostConstruct
    public void init(){
        System.out.println("Customers INIT CALLED");
        loadAllCustomers();
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String requestParam = requestParameters.get("tableEntityId");
        System.out.println(requestParam);
        if(this.tableEntity == null) {
            Integer tableEntityId = Integer.parseInt(requestParam);
            this.tableEntity = tableEntityDAO.findOne(tableEntityId);
        }
    }

    @Transactional
    public String createCustomer(){
        List<TableEntity> tables = new ArrayList<>();
        tables.add(tableEntityDAO.findOne(tableEntity.getId()));
        this.customerToCreate.setTables(tables);
        this.customerToCreate.setReservationTime(customerDate.toString());
        this.customerDAO.persist(customerToCreate);
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("Table id: " + this.tableEntity.getId());
        System.out.println("************************************************************");
        System.out.println("************************************************************");
        System.out.println("************************************************************");

        return "customers?faces-redirect=true&tableEntityId=" + this.tableEntity.getId();
    }
    @Transactional
    public String editCustomer(){
        Customer customerToUpdate = customerDAO.findOne(selectedCustomerId);
        try {
            List<TableEntity> tables = customerToUpdate.getTables();
            tables.add(tableEntity);
            customerToUpdate.setTables(tables);
            this.customerDAO.update(customerToUpdate);
        } catch (OptimisticLockException e) {
            return "customers?faces-redirect=true&tableEntityId=" + customerToUpdate.getId() + "&error=optimistic-lock-exception";
        }
        return "customers?faces-redirect=true&tableEntityId=" + this.tableEntity.getId();
    }

    private void loadAllCustomers(){
        this.allCustomers = customerDAO.loadAll();
    }
}
