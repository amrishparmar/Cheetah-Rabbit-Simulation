package part2.ui;

import part2.animals.*;
import processing.core.*;

public class Statistics {

    /* empty private constructor to prevent instantiation */
    private Statistics() {}

    /* draws the current game statistics to the screen */
    public static void displayMoves(Rabbit rabbit, Cheetah cheetah, PApplet p) {
        p.pushStyle();

        p.fill(0);
        p.textAlign(PConstants.LEFT, PConstants.CENTER);
        p.textSize(p.height/24);
        p.text("Rabbit moves: " + rabbit.getMoves(), 10, 30);
        p.textAlign(PConstants.RIGHT, PConstants.CENTER);
        p.text("Cheetah moves: " + cheetah.getMoves(), p.width-10, 30);

        p.popStyle();
    }

    /* display the current scores on the main menu */
    public static void displayScores(PApplet p) {
        p.pushStyle();

        p.textAlign(PConstants.LEFT, PConstants.CENTER);
        p.textSize(p.height/26);
        p.text("1P R (vs CPU C): " + Scores.getOneVsCpu(), 20, 9 * p.height/10);
        p.text("CPU R (vs CPU C): " + Scores.getCpuRabbitVsCpuCheetah(), 20, 21 * p.height/22);
        p.textAlign(PConstants.RIGHT, PConstants.CENTER);
        p.text("CPU C (vs 1P R): " + Scores.getCpuVsOne(), p.width - 20, 9 * p.height/10);
        p.text("CPU C (vs CPU R): " + Scores.getCpuCheetahVsCpuRabbit(), p.width - 20, 21 * p.height/22);

        p.popStyle();
    }
}
