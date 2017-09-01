package part1.animals;

import processing.core.*;

import part1.graph.*;

public class RabbitHole {

    private GridBlock position; // represents the position of the rabbit hole
    private int colour; // colour of the rabbiit hole
    private boolean active; // whether the rabbit hole is active and available to be interacted with
    protected PApplet p; // processing

    /* default constructor */
    public RabbitHole(GridBlock position, int colour, boolean active, PApplet p) {
        this.position = position;
        this.colour = colour;
        this.active = active;
        this.p = p;
    }

    /* alternative constructor that accepts position and colour, but sets active to false */
    public RabbitHole(GridBlock position, int colour, PApplet p) {
        this(position, colour, false, p);
    }

    /* alternative constructor that accepts colour, but initialises the position to off screen and sets active to false */
    public RabbitHole(int colour, PApplet p) {
        this(new GridBlock(-1, -1, p), colour, p);
    }

    /* display the rabbit hole onto the grid */
    public void draw(Grid grid) {
        p.pushStyle();
        p.fill(colour);
        p.rect(position.getX() * grid.getColWidth(), position.getY() * grid.getRowHeight(), grid.getColWidth(), grid.getRowHeight());
        p.popStyle();
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
