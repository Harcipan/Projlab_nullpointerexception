package use_cases;

public abstract class UseCase {
    private int id;
    private String name;

    UseCase(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract void execute();
}
