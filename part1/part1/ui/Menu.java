package part1.ui;

import processing.core.*;

public class Menu {
    private Button onePlayerOption;
    private Button twoPlayerOption;
    private Button fileAutoOption;
    private Button instructionsOption;
    protected PApplet p;

    /* constructor */
    public Menu(PApplet p) {
        this.p = p;

        int bWidth = 2 * p.width/3;
        int bHeight = p.height/10;

        int btnColour1 = p.color(0, 91, 91);
        int btnColour2 = p.color(255, 236, 220);

        onePlayerOption = new Button(p.width/2 - bWidth/2, 2.5f * p.height/10, bWidth, bHeight, btnColour2, btnColour1, "One Player", p);
        twoPlayerOption = new Button(p.width/2 - bWidth/2, 4 * p.height/10, bWidth, bHeight, btnColour2, btnColour1, "Two Player", p);
        fileAutoOption = new Button(p.width/2 - bWidth/2, 5.5f * p.height/10, bWidth, bHeight, btnColour2, btnColour1, "Auto Mode", p);
        instructionsOption = new Button(p.width/2 - bWidth/2, 7 * p.height/10, bWidth, bHeight, btnColour2, btnColour1, "Instructions", p);
    }

    /* display the menu to the screen */
    public void display() {
        p.pushStyle();
        p.textAlign(PConstants.CENTER, PConstants.CENTER);
        p.textSize(p.height/18);
        p.fill(95, 43, 0);
        p.text("Algorithms and Data Structures\nAssignment Part 1", p.width/2, p.height/9);

        p.textAlign(PConstants.LEFT, PConstants.CENTER);
        p.textSize(p.height/24);
        p.text("1P (vs CPU): " + Scores.getOneVsCpu(), 20, 9 * p.height/10);
        p.text("1P (vs 2P): " + Scores.getOneVsTwo(), 20, 21 * p.height/22);
        p.textAlign(PConstants.RIGHT, PConstants.CENTER);
        p.text("CPU (vs 1P): " + Scores.getCpuVsOne(), p.width - 20, 9 * p.height/10);
        p.text("2P (vs 1P): " + Scores.getTwoVsOne(), p.width - 20, 21 * p.height/22);
        p.popStyle();

        onePlayerOption.display();
        twoPlayerOption.display();
        fileAutoOption.display();
        instructionsOption.display();
    }

    /* getters and setters */

    public Button getOnePlayerOption() {
        return onePlayerOption;
    }

    public Button getTwoPlayerOption() {
        return twoPlayerOption;
    }

    public Button getFileAutoOption() {
        return fileAutoOption;
    }

    public Button getInstructionsOption() {
        return instructionsOption;
    }
}
