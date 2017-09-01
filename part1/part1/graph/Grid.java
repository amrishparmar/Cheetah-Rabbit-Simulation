package part1.graph;

import processing.core.*;

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
    public void draw() {
        // loop through the blocks array and display each as rectangle
        for (int x = 0; x < numBlocks; ++x) {
            for (int y = 0; y < numBlocks; ++y) {
                p.rect(blocks[x][y].getX() * colWidth, blocks[x][y].getY() * rowHeight, colWidth, rowHeight);
            }
        }
    }

    /* getters and setters */

    public int getNumBlocks() {
        return numBlocks;
    }

    public void setNumBlocks(int numBlocks) {
        this.numBlocks = numBlocks;

        // update the heights of rows and columns
        rowHeight = p.height/(this.numBlocks+1);
        colWidth = p.width/this.numBlocks;
    }

    public float getRowHeight() {
        return rowHeight;
    }

    public float getColWidth() {
        return colWidth;
    }
}
