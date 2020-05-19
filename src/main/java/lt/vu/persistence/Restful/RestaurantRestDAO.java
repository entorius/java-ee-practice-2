package lt.vu.persistence.Restful;

import lt.vu.entities.Restaurant;
import lt.vu.persistence.RestaurantDAO;
import lt.vu.persistence.Restful.Model.RestaurantBusinessLogic;
import lt.vu.persistence.Restful.Model.RestaurantModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/restaurant")
public class RestaurantRestDAO {
    @Inject
    private RestaurantDAO resDao;
    @Inject
    RestaurantBusinessLogic resLogic;
    @Path("/{userId}")
    @GET
    @Produces({"application/json"})
    public Response getById(@PathParam("userId") Integer id) {
        RestaurantModel res = resLogic.getById(id);
        return res != null ? Response.ok(res).build() : Response.status(404).build();
    }
    @Path("/")
    @POST
    @Produces({"application/json"})
    public Response insert(RestaurantModel r) {
        RestaurantModel inserted = resLogic.insert(r);
        return Response.ok(inserted).build();
    }
    @Path("/{restaurantId}")
    @PUT
    @Produces({"application/json"})
    public Response put(@PathParam("restaurantId") final Integer resId, @Valid RestaurantModel updatedRes)
    {
        RestaurantModel res = resLogic.insertOrUpdate(resId,updatedRes);
        return Response.ok(res).build();
    }
}
