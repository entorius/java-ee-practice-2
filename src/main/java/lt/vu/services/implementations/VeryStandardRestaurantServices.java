package lt.vu.services.implementations;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Specializes;

@Dependent
@Alternative
@Specializes
@StandardServices
public class VeryStandardRestaurantServices extends StandardRestaurantServices{
    @Override
    public double paymentForInsideTablesPerYear(int tablesNumber) {
        return tablesNumber * 4000;
    }
}
