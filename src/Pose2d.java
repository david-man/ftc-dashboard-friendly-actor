import java.util.*;
import java.io.*;

public class Pose2d {
    double x,y;
    Rotation2d rotation2d;
    public Pose2d(double x, double y, Rotation2d rotation2d)
    {
        this.x=x;
        this.y=y;
        this.rotation2d = rotation2d;
    }
}
class Rotation2d
{
    double rotation;
    public Rotation2d(double rotation)
    {
        this.rotation = rotation;
    }
}
