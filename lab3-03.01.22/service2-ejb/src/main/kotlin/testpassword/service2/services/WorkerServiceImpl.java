package testpassword.service2.services;

import testpassword.service2.services.WorkerService;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless
@Remote(WorkerService.class)
public class WorkerServiceImpl implements WorkerService {

    public int calculate(int a, int b) {
        return a+b;
    }
}
