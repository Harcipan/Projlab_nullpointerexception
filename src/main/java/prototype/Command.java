package prototype;

public abstract class Command {
    protected String name;
    protected String usage;
    protected String description;
    protected App app;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
        this.usage = name;
    }

    public Command(String name, String description, String usage) {
        this.name = name;
        this.description = description;
        this.usage = usage;
    }

    public void setApp(App app){
        this.app = app;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public abstract boolean execute(String[] args);

}
