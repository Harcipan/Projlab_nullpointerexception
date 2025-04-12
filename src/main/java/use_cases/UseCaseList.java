package use_cases;

import java.util.ArrayList;
import java.util.List;

public class UseCaseList {
    List<UseCase> useCases = new ArrayList<>();

    /*
    *       ADD YOUR USE CASES HERE
    * */
    public UseCaseList() {
        useCases.add(new InsectMove()); // 1
        useCases.add(new InsectCutMycelium()); // 2
        useCases.add(new EatingSpore()); // 3
        useCases.add(new EatingSpeedupSpore()); // 4
        useCases.add(new EatingSlowSpore()); // 5
        useCases.add(new EatingFreezingSpore()); // 6
        useCases.add(new EatingCutSpore()); // 7
        useCases.add(new InsectFreezeTimesOut()); // 8
        useCases.add(new MyceliumGrowing()); // 9
        useCases.add(new MyceliumGrowingWithSpore()); // 10
        useCases.add(new DetachedMyceliumDies()); // 11
        useCases.add(new FungusGrowingAMushroom()); // 12
        useCases.add(new FungusSpreadingSpores()); // 13
        useCases.add(new FungusBodyDies()); // 14
        useCases.add(new TektonBreaking()); // 15
        useCases.add(new CannotGrowBodyOnTekton()); // 16
        useCases.add(new OnlyOneMyceliumGrowingOnTekton()); // 17
        useCases.add(new MultipleMyceliumGrowingOnTekton()); // 18
        useCases.add(new TektonBreakingMycelium()); // 19
        useCases.add(new InsectSplitSpore()); // 20
        useCases.add(new HealTileKeepsAlive()); // 21
        useCases.add(new FungusEatsInsect()); // 22
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
