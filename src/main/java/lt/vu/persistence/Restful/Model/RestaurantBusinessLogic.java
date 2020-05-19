package lt.vu.persistence.Restful.Model;

import lombok.SneakyThrows;
import lt.vu.entities.Restaurant;
import lt.vu.persistence.RestaurantDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;
import javax.transaction.Transactional;

@ApplicationScoped
public class RestaurantBusinessLogic {
    @Inject
    RestaurantDAO restaurantDAO;

    @Inject
    AsynchronousRestaurantCaller resAsync;
    @Transactional
    public RestaurantModel getById(Integer resId) {
        Restaurant entity = this.restaurantDAO.findOne(resId);
        this.sendCongratulations(entity);

        return RestaurantModel.buildFromEntity(entity);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public RestaurantModel insertOrUpdate(Integer resId, RestaurantModel resModel) {
        Restaurant entityToUpdate = this.restaurantDAO.findOne(resId);
        Integer version = entityToUpdate == null ? 1 : entityToUpdate.getVersion();
        resModel.setId(resId);
        resModel.setName(resModel.getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Restaurant entity = resModel.convertToEntity(this.restaurantDAO);
        entity.setVersion(version);
        try {
            entity = this.restaurantDAO.update(entity);
            return RestaurantModel.buildFromEntity(entity);
        } catch(RollbackException e) {
            if (e.getCause() instanceof OptimisticLockException) {
                System.out.println("Optimistic Lock Exception, retrying...");
                insertOrUpdate(resId, resModel);
            }
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Transactional
    public RestaurantModel insert(RestaurantModel resModel) {
        Restaurant entity = resModel.convertToEntity(this.restaurantDAO);
        this.restaurantDAO.persist(entity);
        return RestaurantModel.buildFromEntity(entity);
    }

    public void sendCongratulations(Restaurant res) {
        resAsync.CallHello(res);
    }
}
