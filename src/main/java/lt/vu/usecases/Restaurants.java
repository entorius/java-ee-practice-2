package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
//import lt.vu.mybatis.dao.RestaurantMapper;
//import lt.vu.mybatis.dao.TableEntityMapper;
//import lt.vu.mybatis.model.Restaurant;
//import lt.vu.mybatis.model.TableEntity;
import lt.vu.entities.Customer;
import lt.vu.entities.Restaurant;
import lt.vu.entities.TableEntity;
import lt.vu.persistence.RestaurantDAO;
import lt.vu.persistence.TableEntityDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model
public class Restaurants {
    @Inject
    private RestaurantDAO restaurantsDAO;

    @Inject
    private TableEntityDAO tableEntityDAO;

    @Getter
    @Setter
    private Restaurant restaurantToCreate = new Restaurant();

    @Getter
    @Setter
    private List<Integer> restaurantsTableNumbers = new ArrayList<Integer>();

    @Getter
    private List<Restaurant> allRestaurants;

    @PostConstruct
    public void init(){

        loadAllRestaurants();
        countTables();
    }

    @Transactional
    public String createRestaurant(){
        this.restaurantsDAO.persist(restaurantToCreate);
        return "restaurants?faces-redirect=true";
    }

    @Transactional
    public String deleteRestaurant(Integer restaurantId){
        try {
            Restaurant restaurantToDelete = restaurantsDAO.findOne(restaurantId);
            List<TableEntity> tableEntityList = restaurantToDelete.getTables();
            for (Iterator<TableEntity> iterator = tableEntityList.iterator(); iterator.hasNext(); ) {
                TableEntity t = iterator.next();
                tableEntityDAO.removeTables(t.getId());
            }
            this.restaurantsDAO.deleteById(restaurantId);
            return "restaurants?faces-redirect=true";
        }
        catch(Exception e){
            System.out.println("Cannot delete restaurant which tables has customers");
            return "restaurants?faces-redirect=true";
        }
    }

    private void loadAllRestaurants(){
        this.allRestaurants = restaurantsDAO.loadAll();
    }
    private void countTables(){
        for(Restaurant res : this.allRestaurants){
            List<TableEntity> tables = res.getTables();
            Integer tablesCount = tables.size();
            restaurantsTableNumbers.add(tablesCount);
        }

    }
}
