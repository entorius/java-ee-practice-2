package lt.vu.persistence;

import lt.vu.entities.Restaurant;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class RestaurantDAO {
    @Inject
    private EntityManager em;
    @PersistenceUnit(unitName = "LocalPlayersPU")
    private EntityManagerFactory emf;


    public List<Restaurant> loadAll() {
        return em.createNamedQuery("Restaurant.findAll", Restaurant.class).getResultList();
    }

    public void persist(Restaurant restaurant){
        this.em.persist(restaurant);
    }

    public Restaurant findOne(Integer id){
        return em.find(Restaurant.class, id);
    }

    public Restaurant update(Restaurant restaurant){
        return em.merge(restaurant);
    }

    public Restaurant updateRestaurantName(Restaurant restaurant,String newName) throws InterruptedException {
        Restaurant foundRestaurant = this.findOne(restaurant.getId());
        foundRestaurant.setName(newName);
        System.out.println("Lock Mode for update 2: " + foundRestaurant.getVersion());
        System.out.println("Lock Mode for update 2: " + em.getLockMode(foundRestaurant));
        try {
            Thread.sleep(5000);
        } catch (java.lang.InterruptedException e){
            System.out.println("java interrupt exception");
        }
        em.flush();

        return foundRestaurant;
    }

    public void deleteById(Integer id) {
        Restaurant restaurant = em.find(Restaurant.class, id);

        em.remove(restaurant);
    }

}
