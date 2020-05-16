package lt.vu.persistence;

import lt.vu.entities.Restaurant;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class RestaurantDAO {
    @Inject
    private EntityManager em;

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

    public void deleteById(Integer id) {
        Restaurant restaurant = em.find(Restaurant.class, id);

        em.remove(restaurant);
    }

}
