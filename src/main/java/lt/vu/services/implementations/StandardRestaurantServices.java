package lt.vu.services.implementations;


import lt.vu.services.interfaces.RestaurantServices;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;

@Dependent
@Default
@StandardServices
public class StandardRestaurantServices implements RestaurantServices {
    public double paymentForInsideTablesPerYear(int tablesNumber) {
        return 200 * tablesNumber;
    }
}
