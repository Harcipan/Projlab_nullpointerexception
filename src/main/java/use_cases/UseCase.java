package use_cases;

public abstract class UseCase {
    private int id;
    private String name;
    private static Direction persistentDir = Direction.RIGHT;
    public enum Direction {LEFT, RIGHT}

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

    public static void printWrapper(String message, Direction dir, int indentDepth) {
        String arrow = (dir == Direction.LEFT) ? "<-" : "->";
        persistentDir = dir;
        String indent = "\t".repeat(indentDepth);
        System.out.println(indent + arrow + " " + message + " " + arrow);
    }

    // Uses 0 indent depth by default
    public static void printWrapper(String message, Direction dir) {
        printWrapper(message, dir, 0);
    }


    // Remembers the last direction used
    public static void printWrapper(String message) {
        printWrapper(message, persistentDir, 0);
    }

    public abstract void execute();
}
