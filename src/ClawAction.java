import java.util.*;
import java.io.*;
import java.util.function.Predicate;

public class ClawAction extends Action {
    @Override
    public void run() {

    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public double defaultTimeout() {
        return 0;
    }

    public enum ClawStates implements Predicate<ClawStates> {
        topOpen,
        topClosed,
        bottomOpen,
        bottomClosed;

        @Override
        public boolean test(ClawStates clawStates) {
            return clawStates == this;
        }
    }

    double tilt = 0;

    public ClawStates[] states = null;

    public ClawAction(ClawStates... states) {
        this.states = states;
    }

    public ClawAction(double tilt) { this.tilt = tilt; }

    public ClawAction(boolean isBackboard) {
        this(isBackboard ? 1.0 : 0);
    }
    public String toString()
    {
        if(states == null)
        {
            return "ClawTiltAction: " + tilt;
        }
        else
        {
            return "ClawOpenAction: " + (states[0] == ClawStates.topOpen) +", " + (states[1]==ClawStates.bottomOpen);
        }
    }
}
