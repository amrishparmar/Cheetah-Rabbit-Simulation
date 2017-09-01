package part2.graph;

import processing.core.*;

public class NodeBlock extends GridBlock {
    private int gCost; // cost from the starting point
    private int hCost; // cost to the end point
    private NodeBlock parent; // the parent node in a path of NodeBlocks

    /* constructor - calls the parent constructor and then initialises member variables */
    public NodeBlock(int x, int y, boolean walkable, PApplet p) {
        super(x, y, walkable, p);
        gCost = 0;
        hCost = 0;
        parent = null;
    }

    /* constructor - defaults the block to walkable */
    public NodeBlock(int x, int y, PApplet p) {
        this(x, y, true, p);
    }

    /* constructor - creates a NodeBlock from and existing GridBlock */
    public NodeBlock(GridBlock gridBlock, PApplet p) {
        this(gridBlock.getX(), gridBlock.getY(), gridBlock.isWalkable(), p);
    }

    /* override of equals, but calls the parent version */
    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }

    /* returns the sum of the gCost and hCost */
    public int fCost() {
        return gCost + hCost;
    }

    /* getters and setters */

    public int getGCost() {
        return gCost;
    }

    public void setGCost(int gCost) {
        this.gCost = gCost;
    }

    public int getHCost() {
        return hCost;
    }

    public void setHCost(int hCost) {
        this.hCost = hCost;
    }

    public NodeBlock getParent() {
        return parent;
    }

    public void setParent(NodeBlock parent) {
        this.parent = parent;
    }
}
