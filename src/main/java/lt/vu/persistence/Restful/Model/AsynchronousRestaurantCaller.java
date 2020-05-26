package lt.vu.persistence.Restful.Model;

import lt.vu.entities.Restaurant;
import lt.vu.usecases.RestaurantAsyncMethods;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AsynchronousRestaurantCaller {
    @Inject
    RestaurantAsyncMethods resAsync;
    public void CallHello(Restaurant res){
        System.out.println(resAsync);
        resAsync.getAsyncSayLongHello(res);
    }
}
