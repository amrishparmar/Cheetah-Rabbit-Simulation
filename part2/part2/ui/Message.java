package part2.ui;

import processing.core.PApplet;
import processing.core.PConstants;

public class Message {
    private String message;
    private float x;
    private float y;
    private int width;
    private int height;
    private Button toMenu;
    protected PApplet p;

    /* standard constructor */
    public Message(String message, PApplet p) {
        this.message = message;
        this.p = p;

        // set the width and height to be 4/5ths of the window
        width = 4 * this.p.width/5;
        height = 4 * this.p.height/5;

        x = this.p.width/2 - width/2;
        y = this.p.height/2 - height/2;

        // prepare width and height of the button
        int bWidth = 5 * width/6;
        int bHeight = this.p.height/10;

        toMenu = new Button(x + width/2 - bWidth/2, y + height - this.p.height/8, bWidth, bHeight, p.color(255, 236, 220), p.color(0, 91, 91), "Back to menu", this.p);
    }

    /* constructor - calls the other constructor to initialise the message to an empty string */
    public Message(PApplet p) {
        this("", p);
    }

    public void display() {
        p.pushStyle();

        p.stroke(95, 43, 0);
        p.strokeWeight(4);
        p.fill(255);
        p.rect(x, y, width, height);
        p.textAlign(PConstants.CENTER, PConstants.CENTER);
        p.textSize(p.height/20);
        p.fill(95, 43, 0);
        p.text(message, x + 15, y + 15, width - 30, height - 110);

        p.popStyle();

        toMenu.display();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Button getToMenu() {
        return toMenu;
    }
}
