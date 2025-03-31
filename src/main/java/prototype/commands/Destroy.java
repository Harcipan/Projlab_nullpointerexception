package prototype.commands;

import entities.FungusBody;
import entities.GameEntity;
import prototype.Command;

public class Destroy extends Command{
    public Destroy() {
        super("destroy", "Destroy a given entity", "destroy <entity id>");
    }

    @Override
    public boolean execute(String[] args) {
        if(isWrongNumberOfArgs(2, args.length)) return false;
        if(isMapUninitialized()) return false;
        GameEntity entity = parseEntityId(args[1], "Game entity");
        if(entity == null) return false;

        //Let's hope this is sufficient
        entity.getCurrentTile().removeEntity(entity);
        return false;
    }
}
