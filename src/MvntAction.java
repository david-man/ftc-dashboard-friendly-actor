public class MvntAction extends Action{
    private Pose2d target = null;
    private double[] direction = null;
    private Double maxPower = null;
    public MvntAction(Pose2d target, double maxPower) {
        this.target = target;
        this.maxPower = maxPower;
    }

    public MvntAction(Pose2d target) {
        this.target = target;
        this.maxPower = 1.0;
    }

    public MvntAction(double x, double y, double rx) {
        this.direction = new double[]{x, y, rx};
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
        return "MovementAction: " + target.x + ", " + target.y + ", heading: " + target.rotation2d.rotation;
    }
}
