package lt.vu.services.interfaces;

import java.util.List;

public interface CustomerServices {
    double countPaymentSum(List<Double> tablesRequestedSums);
}
