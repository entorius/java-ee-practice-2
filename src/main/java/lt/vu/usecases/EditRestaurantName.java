package lt.vu.usecases;
import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Restaurant;
import lt.vu.persistence.RestaurantDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@Model
public class EditRestaurantName {
    @Inject
    private RestaurantDAO restaurantsDAO;
    @Inject
    private RestaurantAsyncMethods resAsync;
    @Getter
    @Setter
    private Restaurant selectedRestaurant;
    @Getter
    @Setter
    private String selectedRestaurantNameUpdate;
    @Getter
    @Setter
    private Boolean completed;
    @Getter
    @Setter
    private String restaurantManagerMessage;
    @Getter
    private List<Restaurant> allRestaurants;
    @Getter
    @Setter
    private Integer Counter = 0;
    @PostConstruct
    public void init(){
        loadAllRestaurants();
        setCurrentRestaurant();
        restaurantManagerMessage = "Restaurant manger said hello for " + selectedRestaurant.getConNumber() + " Times";

    }
    public void setCurrentRestaurant(){
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        String requestParamId = requestParameters.get("restaurantId");

        System.out.println(requestParamId);
        if(requestParamId != null) {
            int selectedRestaurantId = Integer.parseInt(requestParamId);

            this.selectedRestaurant = allRestaurants.stream()
                    .filter(rest -> rest.getId() == selectedRestaurantId)
                    .findFirst().orElse(null);

            this.selectedRestaurantNameUpdate = selectedRestaurant.getName();
        }

    }
    @Transactional//(REQUIRES_NEW)
    public String editRestaurantName(){
        Restaurant restaurantToUpdate = restaurantsDAO.findOne(selectedRestaurant.getId());
        try {
           this.restaurantsDAO.updateRestaurantName(restaurantToUpdate,selectedRestaurantNameUpdate);
        } catch (OptimisticLockException e) {
            return "editRestaurantName?faces-redirect=true&restaurantId=" + restaurantToUpdate.getId() + "&error=optimistic-lock-exception";
        }
        catch (InterruptedException e) {
            return "editRestaurantName?faces-redirect=true&restaurantId=" + restaurantToUpdate.getId() + "&error=interrupted-exception";
        }
        return "editRestaurantName?faces-redirect=true&restaurantId=" + restaurantToUpdate.getId();
    }
    @Transactional
    public String sayHello(){
        resAsync.getAsyncSayLongHello(selectedRestaurant);
        return "editRestaurantName?faces-redirect=true&restaurantId=" + selectedRestaurant.getId();
    }
    private void loadAllRestaurants(){
        this.allRestaurants = restaurantsDAO.loadAll();
    }
}
