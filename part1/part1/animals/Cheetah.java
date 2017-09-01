package part1.animals;

import processing.core.*;

import part1.graph.*;
import part1.constants.Direction;

public class Cheetah extends Animal {

    /* constructor */
    public Cheetah(int x, int y, PApplet p) {
        super(x, y, p.color(255, 132, 0), p);
    }

    /* display the cheetah on screen */
    public void display(Grid grid) {
        super.display(grid, "C");
    }

    /* chases the rabbit when running in one player mode */
    public void chase(Rabbit rabbit, Grid grid) {
        // get the x and y distances from cheetah to rabbit
        int x = rabbit.getPosition().getX() - getPosition().getX();
        int y = rabbit.getPosition().getY() - getPosition().getY();

        // move along the axis which still has the furthest to travel
        if (Math.abs(x) > Math.abs(y)) {
            if (x > 0) {
                move(Direction.RIGHT, grid);
            }
            else if (x < 0){
                move(Direction.LEFT, grid);
            }
        }
        else {
            if (y > 0) {
                move(Direction.DOWN, grid);
            }
            else if (y < 0){
                move(Direction.UP, grid);
            }
        }
    }
}
