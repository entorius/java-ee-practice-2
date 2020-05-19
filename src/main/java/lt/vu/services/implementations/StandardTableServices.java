package lt.vu.services.implementations;

import lt.vu.interceptors.LoggedInvocation;
import lt.vu.interceptors.MethodLogger;
import lt.vu.services.interfaces.TableServices;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;

@Dependent
@Default
public class StandardTableServices implements TableServices {
    @Override
    @LoggedInvocation
    public double countTablePrice(int seatsCount ) {
        return 4 * seatsCount;
    }



}
