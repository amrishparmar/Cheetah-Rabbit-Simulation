package part2.animals;

import part2.graph.*;
import processing.core.*;

public class RabbitHole extends Animal {
    private boolean active; // whether the rabbit hole is active and available to be interacted with

    /* default constructor */
    public RabbitHole(GridBlock position, int colour, boolean active, PApplet p) {
        super(position, colour, p);
        this.active = active;
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
    public void display(Grid grid) {
        super.display(grid, "H");
    }

    /* getters and setters */

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
