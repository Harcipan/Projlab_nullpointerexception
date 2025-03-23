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
        useCases.add(new InsectMove()); // 1
        useCases.add(new InsectCutMycelium()); // 2
        useCases.add(new EatingSpeedupSpore()); // 3
        useCases.add(new EatingSlowSpore()); // 4
        useCases.add(new EatingFreezingSpore()); // 5
        useCases.add(new EatingCutSpore()); // 6
        useCases.add(new MyceliumDies()); // 11
        useCases.add(new FungusGrowingAMushroom()); // 12
        useCases.add(new FungusSpreadingSpores()); // 13
        useCases.add(new FungusBodyDies()); // 14
        useCases.add(new TektonBreaking()); // 15
        useCases.add(new CannotGrowBodyOnTekton()); // 16
        useCases.add(new OnlyOneMyceliumGrowingOnTekton()); // 17
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
