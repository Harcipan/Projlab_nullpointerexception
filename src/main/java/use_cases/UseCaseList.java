package use_cases;

import java.util.ArrayList;
import java.util.List;

import static use_cases.UseCase.*;

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
        useCases.add(new FungusSpreadingSpores());
        useCases.add(new FungusBodyDies());
        useCases.add(new TektonBreaking());
    }

    public List<UseCase> getUseCases() {
        return useCases;
    }

    // sort them based on id
    public void sortUseCases() {
        System.out.println("Sorting and verifying use cases...");
        // checking for duplicate ids
        for (int i = 0; i < useCases.size(); i++) {
            for (int j = i + 1; j < useCases.size(); j++) {
                if (useCases.get(i).getID() == useCases.get(j).getID()) {
                    throw new IllegalArgumentException("Duplicate use case id found: " + useCases.get(i).getID());
                }
            }
        }
        // checking for duplicate names even for lowercase matches
        for (int i = 0; i < useCases.size(); i++) {
            for (int j = i + 1; j < useCases.size(); j++) {
                if (useCases.get(i).getName().equalsIgnoreCase(useCases.get(j).getName())) {
                    throw new IllegalArgumentException("Duplicate use case name found: " + useCases.get(i).getName());
                }
            }
        }
        useCases.sort((a, b) -> a.getID() - b.getID());
    }
}
