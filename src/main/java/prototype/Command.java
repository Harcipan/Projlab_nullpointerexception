package prototype;

import java.util.Scanner;

import entities.GameEntity;

public abstract class Command {
    protected String name;
    protected String usage;
    protected String description;

    protected App app;
    protected Scanner scanner;

    private static int entityId = 0;

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

    protected void usage(){
        System.out.println("Usage: " + usage);
    }
    protected void wrongNumbersOfArgs(int expected, int got){
        System.out.println("Wrong number of arguments (expected " + expected + ", got " + got + " arguments)");
        usage();
    }
    protected boolean isMapUninitialized(){
        if(app.getMap() == null){
            System.out.println("You must create a map first by running \"create Map\"!");
            return true;
        }
        return false;
    }
    protected GameEntity assignId(GameEntity entity){
        System.out.println("Assigned the number \"" + entityId + "\" to the " + entity.getClass().getSimpleName() + " game entity.");
        entity.setId(entityId++);
        return entity;
    }

    public void setApp(App app){
        this.app = app;
    }
    
    public void setScanner(Scanner scanner){
        this.scanner = scanner;
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
