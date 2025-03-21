package use_cases;


public class MyceliumDies extends UseCase {

    public MyceliumDies() {
        super(1, "Mycelium Dies");
    }

    public void execute() {
        System.out.println("Mycelium dies.");
    }
}

