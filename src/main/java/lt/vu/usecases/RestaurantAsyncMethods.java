package lt.vu.usecases;

import lt.vu.entities.Restaurant;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


public class RestaurantAsyncMethods {
    @Async("threadPoolTaskExecutor")
    public String getAsyncSayLongHello(Restaurant res) {
        System.out.println("Execute method with configured executor - "
                + Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
            return "Restaurant " + res.getName() + " manager say hello to you";
        } catch (InterruptedException e) {
            return " Interruption exception";
        }

    }
}
