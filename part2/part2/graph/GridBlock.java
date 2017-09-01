package part2.graph;

import processing.core.*;

public class GridBlock {
    private int x; // x coordinate of the block
    private int y; // y coordinate of the block
    private boolean walkable; // whether an Animal can walk on this block
    protected PApplet p; // processing

    /* constructor */
    public GridBlock(int x, int y, boolean walkable, PApplet p) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;
        this.p = p;
    }

    /* constructor - defaults the walkability to true */
    public GridBlock(int x, int y, PApplet p) {
        this(x, y, true, p);
    }

    /* checks if the current grid block has the same position as another */
    public boolean equalTo(GridBlock other) {
        return x == other.getX() && y == other.getY();
    }

    /* override of the Object equals method */
    @Override
    public boolean equals(Object other) {
        // if they are the same object
        if (other == this) {
            return true;
        }
        // if the object is not an instance of a GridBlock or a NodeBlock
        if (!(other instanceof GridBlock) || !(other instanceof NodeBlock)) {
            return false;
        }
        GridBlock gb = (GridBlock) other;

        // make use of our equalTo method and return it's value
        return equalTo(gb);
    }

    /* returns representation of the member variables as a string */
    @Override
    public String toString() {
        return "GridBlock{" +
                "x=" + x +
                ", y=" + y +
                ", walkable=" + walkable +
                '}';
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

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }
}
