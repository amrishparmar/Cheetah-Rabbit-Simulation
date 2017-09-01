package part1.ui;

import part1.animals.*;
import processing.core.*;

public class Statistics {

    /* draws the current game statistics to the screen */
    public static void display(Rabbit rabbit, Cheetah cheetah, PApplet p) {
        p.pushStyle();

        p.fill(0);
        p.textAlign(PConstants.LEFT, PConstants.CENTER);
        p.textSize(p.height/24);
        p.text("Rabbit moves: " + rabbit.getMoves(), 10, 30);
        p.textAlign(PConstants.RIGHT, PConstants.CENTER);
        p.text("Cheetah moves: " + cheetah.getMoves(), p.width-10, 30);

        p.popStyle();
    }
}
