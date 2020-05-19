package lt.vu.usecases;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletableFuture;


@ApplicationScoped
public class RestaurantAsyncMethods {

    @Async
    public void getAsyncSayLongHello(String restaurantName) {
        CompletableFuture<String> invitationTask = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Restaurant " + restaurantName + " manager say hello to you");
            return "Restaurant " + restaurantName + " manager say hello to you";
        });


    }
}
