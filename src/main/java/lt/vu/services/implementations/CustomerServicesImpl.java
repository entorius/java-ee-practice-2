package lt.vu.services.implementations;

import lt.vu.services.interfaces.CustomerServices;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import java.util.List;

@Dependent
@Default
public class CustomerServicesImpl implements CustomerServices {

    @Override
    public double countPaymentSum(List<Double> tablesRequestedSums) {
        double totalAmount = 0;
        for(Double am : tablesRequestedSums){
            totalAmount += am;
        }
        return totalAmount;
    }
}
