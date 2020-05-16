package lt.vu.persistence;


import lt.vu.entities.Customer;
import lt.vu.entities.TableEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Iterator;
import java.util.List;

@ApplicationScoped
public class TableEntityDAO {
    @Inject
    private EntityManager em;

    public void persist(TableEntity tableEntity){
        this.em.persist(tableEntity);
    }

    public TableEntity findOne(Integer id){
        return em.find(TableEntity.class, id);
    }

    public TableEntity update(TableEntity tableEntity){
        return em.merge(tableEntity);
    }

    public void removeTables(Integer tableId){
        System.out.println("\n\n\n\nStarted delete table Customers\n\n\n\n");
        TableEntity tableEntity = em.find(TableEntity.class, tableId);
        List<Customer> customersList = tableEntity.getCustomers();
        for (Iterator<Customer> iterator = customersList.iterator(); iterator.hasNext(); ){
            Customer customer = iterator.next();
            tableEntity.removeCustomer(customer);

        }
        System.out.println("\n\n\n\n\nDeleted table Customers\n\n\n\n\n");
    }

}
