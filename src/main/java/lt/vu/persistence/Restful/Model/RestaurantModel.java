package lt.vu.persistence.Restful.Model;


import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Restaurant;
import lt.vu.entities.TableEntity;
import lt.vu.persistence.RestaurantDAO;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RestaurantModel {
    private Integer id;
    @NotNull
    private String name;
    @Transactional
    public Restaurant convertToEntity(RestaurantDAO resDao) {
        Restaurant restaurant = resDao.findOne(id);
        restaurant = restaurant == null ? new Restaurant() : restaurant;
        restaurant.setId(id == null ? 0 : id);
        restaurant.setName(name);
        restaurant.setTables(restaurant.getTables() == null ? new ArrayList<>() : restaurant.getTables());
        return restaurant;
    }

    public static RestaurantModel buildFromEntity(Restaurant restaurant) {
        if (restaurant == null) return null;

        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setId(restaurant.getId());
        restaurantModel.setName(restaurant.getName());

        return restaurantModel;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
