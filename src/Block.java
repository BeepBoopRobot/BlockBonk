public class Block {
    private double pos;
    private long mass;
    private double speed;
    private double blockWidth;
    private boolean side; // true for right side, false for left side


    double getBlockWidth() {
        return blockWidth;
    }

    Block(long mass, double speed, double pos, boolean side) {
        this.pos = pos;
        this.speed = speed;
        this.mass = mass;
        this.blockWidth = 60;
        this.side = side;
    }

    void update() {
        pos += speed/1000;
    }

    public long getMass() {
        return mass;
    }


    double oldSpeed() {
        return speed;
    }

    void newSpeed(double speed) {
        this.speed = speed;
    }

    double getD() {
        if(side) {
            return pos + blockWidth;
        } else {
            return pos;
        }
    }

    double getPos() {
        return pos + 100;
    }
}
