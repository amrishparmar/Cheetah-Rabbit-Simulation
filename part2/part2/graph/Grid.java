package part2.graph;

import part2.animals.*;
import processing.core.*;

import javax.xml.soap.Node;
import java.util.ArrayList;

public class Grid {
    private int numBlocks; // number of blocks in each dimension
    private float rowHeight; // height of each row
    private float colWidth; // width of each column
    private GridBlock[][] blocks; // 2d array of blocks
    protected PApplet p; // processing

    /* constructor */
    public Grid(int numBlocks, PApplet p) {
        this.p = p;

        this.numBlocks = numBlocks;
        rowHeight = p.height/(numBlocks+1);
        colWidth = p.width/numBlocks;

        blocks = new GridBlock[numBlocks][numBlocks];

        // create the 2d array of blocks with a y offset of 1
        for (int x = 0; x < numBlocks; ++x) {
            for (int y = 0; y < numBlocks; ++y) {
                blocks[x][y] = new GridBlock(x, y+1, p);
            }
        }
    }

    /* display grid blocks to the screen */
    public void display() {
        // loop through the blocks array and display each as rectangle
        for (int x = 0; x < numBlocks; ++x) {
            for (int y = 0; y < numBlocks; ++y) {
                p.pushStyle();
                // display unmoveable blocks as black
                if (blocks[x][y].isWalkable()) {
                    p.fill(255);
                }
                else {
                    p.fill(0);
                }
                p.rect(blocks[x][y].getX() * colWidth, blocks[x][y].getY() * rowHeight, colWidth, rowHeight);
                p.popStyle();

            }
        }
    }

    /* helper function for paint obstacles - translates program coordinates to grid coordinates  */
    private GridBlock translateXYToCoords(float x, float y) {
        GridBlock gb = new GridBlock(0, 0, p);

        int coordX = (int)(x/colWidth);
        int coordY = (int)(y/rowHeight);

        gb.setX(coordX);
        gb.setY(coordY);

        return gb;
    }

    /* paints the obstacles setting the block to walkable or not depending on which mouse button was pressed*/
    public void paintObstacles(float x, float y, Rabbit rabbit, Cheetah cheetah, RabbitHole hole, int mouseButton) {
        if (x > 0 && x < p.width && y > rowHeight && y < p.height) {
            GridBlock gb = translateXYToCoords(x, y);
            if (!gb.equalTo(rabbit.getPosition()) && !gb.equalTo(cheetah.getPosition()) && !gb.equalTo(hole.getPosition())) {
                // translate into the correct world y coord
                gb.setY(gb.getY() - 1);

                // set the block as unwalkable if left mouse button pressed or walkable if right mouse button pressed
                if (mouseButton == PConstants.LEFT) {
                    blocks[gb.getX()][gb.getY()].setWalkable(false);
                }
                else if (mouseButton == PConstants.RIGHT) {
                    blocks[gb.getX()][gb.getY()].setWalkable(true);
                }
            }
        }
    }

    /* returns a list of walkable neighbours around a given node block */
    public ArrayList<NodeBlock> getWalkableNeighbours(NodeBlock nodeBlock) {
        ArrayList<NodeBlock> neighbours = new ArrayList<>();

        // iterate over the 8 surrounding blocks
        for (int x = -1; x <= 1; ++x) {
            for (int y = -1; y <= 1; ++y) {
                // only want the ones directly above, below, left or right
                if ((x == 0 || y == 0) && !(x == 0 && y == 0)) {
                    // store the current coords of the neighbour block
                    int cx = nodeBlock.getX() + x;
                    int cy = nodeBlock.getY() + y;

                    // if the neighbour is a valid block inside the grid
                    if (cx >= 0 && cx < numBlocks && cy >= 1 && cy <= numBlocks) {
                        // create a node block from the grid block
                        NodeBlock neighbour = new NodeBlock(blocks[cx][cy-1], p);
                        // if the neighbour is a walkable block then add it to our array list
                        if (neighbour.isWalkable()) {
                            neighbours.add(neighbour);
                        }
                    }
                }
            }
        }

        return neighbours;
    }

    /* getters and setters */

    public int getNumBlocks() {
        return numBlocks;
    }

    public void setNumBlocks(int numBlocks) {
        this.numBlocks = numBlocks;

        // update the heights of rows and columns
        rowHeight = p.height/(this.numBlocks + 1);
        colWidth = p.width/this.numBlocks;
    }

    public float getRowHeight() {
        return rowHeight;
    }

    public float getColWidth() {
        return colWidth;
    }

    public GridBlock[][] getBlocks() {
        return blocks;
    }
}
