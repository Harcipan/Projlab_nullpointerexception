package use_cases;

public abstract class UseCase {
    private int id;
    private String name;
    private static int persistentIndentDepth = 0;
    private static ArrowDirection persistentDir = ArrowDirection.RIGHT;
    public enum Indent {UNINDENT, INDENT, KEEP}
    public enum ArrowDirection {LEFT, RIGHT}

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

    public static void printWrapper(String message, ArrowDirection dir, int indentDepth) {
        String arrow = (dir == ArrowDirection.LEFT) ? "<-" : "->";
        persistentDir = dir;
        persistentIndentDepth = indentDepth;
        String indent = "\t".repeat(indentDepth);
        System.out.println(indent + arrow + " " + message + " ");
    }

    // Uses both persistent direction and depth
    public static void printWrapper(String message, ArrowDirection dir) {
        printWrapper(message, dir, persistentIndentDepth);
    }

    // Remembers the last direction used and uses the last direction by default
    public static void printWrapper(String message) {
        printWrapper(message, persistentDir, persistentIndentDepth);
    }

    // Relative depth of the printWrapper with stepping after execution
    public static void printWrapper(String message, ArrowDirection dir, Indent indentDir) {
        printWrapper(message, dir, persistentIndentDepth);

        // Indentation change happens after the message is printed
        if (indentDir == Indent.UNINDENT) {
            persistentIndentDepth--;
        } else if (indentDir == Indent.INDENT) {
            persistentIndentDepth++;
        } else if (indentDir == Indent.KEEP) {
            // Do nothing
        }
    }


    public abstract void execute();
}
