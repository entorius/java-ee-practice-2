package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Restaurant;
import lt.vu.entities.TableEntity;
import lt.vu.persistence.RestaurantDAO;
import lt.vu.persistence.TableEntityDAO;
import lt.vu.services.interfaces.TableServices;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Model
public class Tables {
    @Inject
    private RestaurantDAO restaurantDAO;

    @Inject
    private TableEntityDAO tableEntityDAO;

    @Inject
    private TableServices tableServices;

    @Getter
    @Setter
    private List<Double> tablePrices = new ArrayList<Double>();

    @Getter
    @Setter
    private TableEntity tableEntityToCreate = new TableEntity();

    @Getter
    @Setter
    private Restaurant restaurant;

    @PostConstruct
    public void init(){
        System.out.println("Tables INIT CALLED");
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer restaurantId = Integer.parseInt(requestParameters.get("restaurantId"));
        this.restaurant = restaurantDAO.findOne(restaurantId);
        for(TableEntity t : this.restaurant.getTables()){
            double tablePrice = tableServices.countTablePrice(t.getCapacity());
            tablePrices.add(tablePrice);
        }
    }

    @Transactional
    public String createTableEntity(){
        tableEntityToCreate.setRestaurant(this.restaurant);
        this.tableEntityDAO.persist(tableEntityToCreate);
        return "tables?faces-redirect=true&restaurantId=" + this.restaurant.getId();
    }

}
