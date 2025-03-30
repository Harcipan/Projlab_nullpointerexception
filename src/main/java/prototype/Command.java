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
    protected boolean isWrongNumberOfArgs(int expected, int got){
        if(expected != got){
            System.out.println("Wrong number of arguments (expected " + expected + ", got " + got + " arguments)");
            usage();
            return true;
        }else{
            return false;
        }
    }
    protected boolean isMapUninitialized(){
        if(app.getMap() == null){
            System.out.println("You must create a map first by running \"create Map\"!");
            return true;
        }
        return false;
    }
    protected void announceIdAssign(GameEntity entity){
        System.out.println("Created a \"" + entity.getClass().getSimpleName()  + "\" entity with ID " + entity.getId());
    }
    protected GameEntity assignId(GameEntity entity){
        entity.setId(entityId++);
        announceIdAssign(entity);
        return entity;
    }
    protected int askForId(){
        return entityId++;
    }
    protected Integer parsePositiveNumber(String input, String forWhat){
        Integer ret;
        try {
            ret = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("This is not a number. The " + forWhat.toLowerCase() + " can only be a number.");
            return null;
        }
        if (ret < 0) {
            System.out.println("The " +  forWhat.toLowerCase()  + " must be positive");
            return null;
        }
        return ret;
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
