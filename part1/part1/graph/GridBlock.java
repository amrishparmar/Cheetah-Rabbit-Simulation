package part1.graph;

import processing.core.*;

public class GridBlock {
    private int x; // x coordinate of the block
    private int y; // y coordinate of the block
    protected PApplet p; // processing

    /* constructor */
    public GridBlock(int x, int y, PApplet p) {
        this.x = x;
        this.y = y;
        this.p = p;
    }

    /* checks if the current grid block has the same position as another */
    public boolean equalTo(GridBlock other) {
        return x == other.getX() && y == other.getY();
    }

    /* getters and setters */

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
