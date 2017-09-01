package part2.animals;

import part2.collections.LinkedList;
import part2.collections.ListItem;
import part2.constants.*;
import part2.graph.*;
import processing.core.*;

abstract public class Animal {

    private GridBlock position; // a GridBlock representing the position of the Animal
    private int colour; // the colour of the animal
    private int moves = 0; // the number of moves that the animal has made so far
    private static int totalMoves = 0; // the total number of moves that all Animals have made
    private LinkedList<GridBlock> path;
    protected PApplet p; // processing

    public Animal(GridBlock position, int colour, PApplet p) {
        this.position = position;
        this.colour = colour;
        this.path = null;
        this.p = p;

        totalMoves = 0;
    }

    public Animal(int x, int y, int colour, PApplet p) {
        this(new GridBlock(x, y, p), colour, p);
    }

    /* update the position of the Animal */
    public boolean move(Direction direction, Grid grid) {
        // temp variable store the position of an adjacent grid block
        GridBlock temp = null;

        // check the direction and assign the adjacent block to temp
        switch (direction) {
            case LEFT:
                if (position.getX() > 0) {
                    temp = grid.getBlocks()[position.getX() - 1][position.getY() - 1];
                }
                break;
            case RIGHT:
                if (position.getX() < grid.getNumBlocks() - 1) {
                    temp = grid.getBlocks()[position.getX() + 1][position.getY() - 1];
                }
                break;
            case UP:
                if (position.getY() > 1) {
                    temp = grid.getBlocks()[position.getX()][position.getY() - 2];
                }
                break;
            case DOWN:
                if (position.getY() < grid.getNumBlocks()) {
                    temp = grid.getBlocks()[position.getX()][position.getY()];
                }
                break;
        }

        // if we found a valid move and updated temp
        if (temp != null) {
            // ensure that the block is one we are allowed to move to before moving positions
            if (temp.isWalkable()) {
                position.setX(temp.getX());
                position.setY(temp.getY());
                incrementMoves();
                return true;
            }
        }

        return false;
    }

    /* overload of the move method which sets the position of the animal to the new position */
    public boolean move(GridBlock newPosition) {
        if (newPosition.isWalkable()) {
            position = newPosition;
            incrementMoves();
            return true;
        }
        return false;
    }

    /* draw out the path of the animal to it's destination */
    private void displayPath(Grid grid) {
        LinkedList<GridBlock> path = getPath();

        // if the path exists
        if (path != null) {
            // if it's not an empty path
            if (!path.isEmpty()) {
                ListItem current = path.getHead();
                // continue looping until we reach the end of the path
                while (true) {
                    // loop through the grid
                    for (int x = 0; x < grid.getNumBlocks(); ++x) {
                        for (int y = 0; y < grid.getNumBlocks(); ++y) {
                            GridBlock gridBlock = grid.getBlocks()[x][y];
                            // if the current grid block matches the current element of the grid then draw the rectangle
                            if (gridBlock.equalTo((GridBlock)current.getItem())) {
                                p.pushStyle();
                                p.fill((colour * 50) % PConstants.MAX_INT, 50);
                                p.rect(gridBlock.getX() * grid.getColWidth(), gridBlock.getY() * grid.getRowHeight(), grid.getColWidth(), grid.getRowHeight());
                                p.popStyle();
                            }
                        }
                    }
                    // if the next item is null if we reached the end of the path
                    if (current.getNext() == null) {
                        break;
                    }
                    // otherwise go to the next block in the path
                    else {
                        current = current.getNext();
                    }
                }
            }
        }
    }

    /* draws the animal to the grid, represented as a rectangle */
    protected void display(Grid grid, String character) {
        p.pushStyle();
        p.fill(colour);
        p.rect(position.getX() * grid.getColWidth(), position.getY() * grid.getRowHeight(), grid.getColWidth(), grid.getRowHeight());
        p.fill((colour * 50) % PConstants.MAX_INT);
        p.textAlign(PConstants.CENTER, PConstants.CENTER);
        p.textSize(p.height/24);
        p.text(character, position.getX() * grid.getColWidth() + 0.5f * grid.getColWidth(), position.getY() * grid.getRowHeight() + 0.5f * grid.getRowHeight());
        p.popStyle();

        displayPath(grid);
    }

    /* increments the move counters by 1 */
    public void incrementMoves() {
        ++moves;
        ++totalMoves;
    }

    /* getters and setters */

    public GridBlock getPosition() {
        return position;
    }

    public void setPosition(GridBlock position) {
        this.position = position;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public LinkedList<GridBlock> getPath() {
        return path;
    }

    public void setPath(LinkedList<GridBlock> path) {
        this.path = path;
    }

    public int getMoves() {
        return moves;
    }

    public static int getTotalMoves() {
        return totalMoves;
    }

}
