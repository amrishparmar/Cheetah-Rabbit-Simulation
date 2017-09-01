package part1.animals;

import processing.core.*;

import part1.graph.*;
import part1.constants.*;

abstract public class Animal {

    private GridBlock position; // a GridBlock representing the position of the Animal
    private int colour; // the colour of the animal
    private int moves = 0; // the number of moves that the animal has made so far
    private static int totalMoves = 0; // the total number of moves that all Animals have made
    protected PApplet p; // processing

    /* default constructor */
    public Animal(int x, int y, int colour, PApplet p) {
        this.position = new GridBlock(x, y, p);
        this.colour = colour;
        this.p = p;

        totalMoves = 0;
    }

    /* update the position of the Animal */
    public boolean move(Direction direction, Grid grid) {
        // check the direction and move accordingly
        switch (direction) {
            case LEFT:
                if (position.getX() > 0) {
                    position.setX(position.getX() - 1);
                    incrementMoves();
                    return true;
                }
                break;
            case RIGHT:
                if (position.getX() < grid.getNumBlocks() - 1) {
                    position.setX(position.getX() + 1);
                    incrementMoves();
                    return true;
                }
                break;
            case UP:
                if (position.getY() > 1) {
                    position.setY(position.getY() - 1);
                    incrementMoves();
                    return true;
                }
                break;
            case DOWN:
                if (position.getY() < grid.getNumBlocks()) {
                    position.setY(position.getY() + 1);
                    incrementMoves();
                    return true;
                }
                break;
        }
        return false;
    }

    /* draws the animal to the grid, represented as a rectangle */
    protected void display(Grid grid, String character) {
        p.pushStyle();
        p.fill(colour);
        p.rect(position.getX() * grid.getColWidth(), position.getY() * grid.getRowHeight(), grid.getColWidth(), grid.getRowHeight());
        p.fill((colour * 50) % PConstants.MAX_INT);
        p.textAlign(PConstants.CENTER, PConstants.CENTER);
        p.text(character, position.getX() * grid.getColWidth() + 0.5f * grid.getColWidth(), position.getY() * grid.getRowHeight() + 0.5f * grid.getRowHeight());
        p.popStyle();
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

    public int getMoves() {
        return moves;
    }

    public static int getTotalMoves() {
        return totalMoves;
    }

}
