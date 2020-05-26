package lt.vu.usecases;

import lt.vu.entities.Restaurant;
import lt.vu.persistence.RestaurantDAO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;


@ApplicationScoped
public class RestaurantAsyncMethods {

    @Inject
    RestaurantDAO resDAO;
    @PersistenceUnit(unitName = "LocalPlayersPU")
    private EntityManagerFactory emf;
    public Future<String> getAsyncSayLongHello(Restaurant res) {

        CompletableFuture<String> congradsTask = CompletableFuture.supplyAsync(() -> {
            try {
                Integer resId = res.getId();
                Thread.sleep(5000);
                System.out.println("Transaction started");
                return "something";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Restaurant " + res.getName() + " manager say hello to you";
        });
        congradsTask.thenAccept(finres -> {
            try {
                System.out.println(finres);
                Integer conNumber = res.getConNumber();
                if(conNumber == null){
                    conNumber = 0;
                }
                conNumber = conNumber + 1;
                res.setConNumber(conNumber);
                // At this point, @RequestScoped em is likely disposed
                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();
                em.merge(res);
                em.getTransaction().commit();
                em.close();
                System.out.println("Transaction completed");
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        });

        return congradsTask;
    }
}
