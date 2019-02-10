public class Block {
    private double pos;
    private long mass;
    private double speed;
    private double blockWidth;

    public double getBlockWidth() {
        return blockWidth;
    }

    private boolean side; // true for right side, false for left side

    Block(long mass, double speed, double pos, boolean side) {
        this.pos = pos;
        this.speed = speed;
        this.mass = mass;
        this.blockWidth = 60;
    }

    void update() {
        pos += speed;
    }

    public long getMass() {
        return mass;
    }


    public double oldSpeed() {
        return speed;
    }

    public void newSpeed(double speed) {
        this.speed = speed;
    }
}
