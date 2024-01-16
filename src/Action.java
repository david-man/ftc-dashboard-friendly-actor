import java.util.*;
import java.io.*;

public abstract class Action {
    public boolean perpetual = false;
    public Double timeout = null;

    public abstract void run();

    public abstract boolean isDone();

    public abstract double defaultTimeout();
}
