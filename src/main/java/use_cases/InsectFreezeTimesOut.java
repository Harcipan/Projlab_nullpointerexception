package use_cases;

import entities.Insect;
import map.Map;

public class InsectFreezeTimesOut extends UseCase {
    public InsectFreezeTimesOut()
    {
        super(7, "Insect freeze times out");
    }

    @Override
    public void execute() {
       //create map 
       Map m=new Map();

       //create insect
       Insect i=new Insect();

       m.update(i);
    }

    
}
