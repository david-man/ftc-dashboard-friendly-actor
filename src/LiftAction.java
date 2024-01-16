import java.util.*;
import java.io.*;

public class LiftAction extends Action{
    int height;
    double power;
    public LiftAction(int height, double power) {
        this.height = height;
        this.power = power;
    }

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
    public String toString()
    {
        return "LiftAction: " + height + ", " + power;
    }
}
