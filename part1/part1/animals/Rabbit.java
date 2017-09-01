package part1.animals;

import part1.graph.*;
import processing.core.*;

public class Rabbit extends Animal {

    /* constructor */
    public Rabbit(int x, int y, PApplet p) {
        super(x, y, p.color(204, 191, 177), p);
    }

    /* displays the rabbit on screen */
    public void display(Grid grid) {
        super.display(grid, "R");
    }

    /* returns true if the rabbit's position is the same as the position of the rabbit hole */
    public boolean escaped(RabbitHole hole) {
        if (getPosition().equalTo(hole.getPosition())) {
            setColour(p.color(0, 255, 0));
            return true;
        }
        return false;
    }

    public boolean hasBeenCaught(Cheetah cheetah) {
        if (getPosition().equalTo(cheetah.getPosition())) {
            setColour(p.color(255, 0, 0));
            return true;
        }
        return false;
    }
}
