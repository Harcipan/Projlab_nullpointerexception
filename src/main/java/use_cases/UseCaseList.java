package use_cases;

import java.util.ArrayList;
import java.util.List;

public class UseCaseList {
    List<UseCase> useCases = new ArrayList<>();

    /*
    *       ADD YOUR USE CASES HERE
    * */
    public UseCaseList() {
        useCases.add(new MyceliumDies());
    }

    public List<UseCase> getUseCases() {
        return useCases;
    }
}
