package lt.vu.services.implementations;

import lt.vu.services.interfaces.CustomerServices;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
@Dependent
@Decorator
public class CustomerServicesDecorator implements CustomerServices {
    @Inject
    @Delegate
    private CustomerServices customerServices;


    @Override
    public double countPaymentSum(List<Double> tablesRequestedSums) {
        return customerServices.countPaymentSum(tablesRequestedSums) + 1;
    }
}
