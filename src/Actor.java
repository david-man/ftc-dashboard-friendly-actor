import java.util.*;
import java.io.*;

public class Actor {
    ArrayList<ArrayList<Action>> actions = new ArrayList<>();
    int actionOn = 1;
    public Actor()
    {

    }
    public Actor add(Action action, Double timeout, boolean parallel, boolean perpetual) {
        action.perpetual = perpetual; // this way both are in the add params not the action params
//        action.timeout = timeout != null ? timeout : defaultTimeout;
        if (timeout != null) {
            action.timeout = timeout;
        } else {
            action.timeout = Double.NEGATIVE_INFINITY; // set at runttime to globaldefault or actiondefault
        }
        if (parallel) {
            actions.get(actions.size() - 1).add(action);
        } else {
            actions.add(new ArrayList<>());
            actions.get(actions.size() - 1).add(action);
        }
        return this;
    }

    public Actor add(Action action, boolean parallel, boolean perpetual) {
        return this.add(action, null, parallel, perpetual);
    }

    public Actor add(Action action, boolean parallel) {
        return this.add(action, null,  parallel, false);
    }

    public Actor add(Action action, Double timeout, boolean parallel) {
        return this.add(action, timeout, parallel, false);
    }

    public Actor add(Action action, Double timeout) {
        return this.add(action, timeout, false, false);
    }

    public Actor add(Action action) {
        return this.add(action, null);
    }
    public int run()
    {
        if (actions == null || actions.size() == 0) {
            return 0;
        }
        ArrayList<Action> step = actions.get(0);
        boolean stepDone = true;
        for(Action action : step)
        {
            System.out.println("RUNNING STEP #: " + actionOn);
            System.out.println("RUNNING: " + action.toString());

        }
        if(stepDone){
            System.out.println("DONE RUNNING CURRENT STEP.");
            actions.remove(0);
            actionOn++;
            return actionOn;
        }
        else
        {
            return -1;
        }

    }
}
