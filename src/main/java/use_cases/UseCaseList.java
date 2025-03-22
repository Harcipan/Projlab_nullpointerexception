package use_cases;

import java.util.ArrayList;
import java.util.List;

public class UseCaseList {
    List<UseCase> useCases = new ArrayList<>();

    /*
    *       ADD YOUR USE CASES HERE
    * */
    public UseCaseList() {
        useCases.add(new InsectCutMycelium());
        useCases.add(new InsectMove());
        useCases.add(new MyceliumDies());
        useCases.add(new FungusGrowingAMushroom());
    }

    public List<UseCase> getUseCases() {
        return useCases;
    }

    // sort them based on id
    public void sortUseCases() {
        useCases.sort((a, b) -> a.getID() - b.getID());
    }
}
