package part2.ui;

import processing.core.*;

public class Menu {
    private Button gameOption;
    private Button simulationOption;
    private Button fastCheetahOption;
    private Button instructionsOption;
    protected PApplet p;

    /* constructor */
    public Menu(PApplet p) {
        this.p = p;

        int bWidth = 2 * p.width/3;
        int bHeight = p.height/10;

        int btnColour1 = p.color(0, 91, 91);
        int btnColour2 = p.color(255, 236, 220);

        gameOption = new Button(p.width/2 - bWidth/2, 2.5f * p.height/10, bWidth, bHeight, btnColour2, btnColour1, "Game", p);
        simulationOption = new Button(p.width/2 - bWidth/2, 4 * p.height/10, bWidth, bHeight, btnColour2, btnColour1, "Simulation", p);
        fastCheetahOption = new Button(p.width/2 - bWidth/2, 5.5f * p.height/10, bWidth, bHeight, btnColour2, btnColour1, "Fast Cheetah Simulation", p);
        instructionsOption = new Button(p.width/2 - bWidth/2, 7 * p.height/10, bWidth, bHeight, btnColour2, btnColour1, "Instructions", p);
    }

    /* display the menu to the screen */
    public void display() {
        p.pushStyle();
        p.textAlign(PConstants.CENTER, PConstants.CENTER);
        p.textSize(p.height/18);
        p.fill(95, 43, 0);
        p.text("Algorithms and Data Structures\nAssignment Part 2", p.width/2, p.height/9);

        gameOption.display();
        simulationOption.display();
        fastCheetahOption.display();
        instructionsOption.display();

        Statistics.displayScores(p);
    }

    /* getters and setters */

    public Button getGameOption() {
        return gameOption;
    }

    public Button getSimulationOption() {
        return simulationOption;
    }

    public Button getFastCheetahOption() {
        return fastCheetahOption;
    }

    public Button getInstructionsOption() {
        return instructionsOption;
    }
}
