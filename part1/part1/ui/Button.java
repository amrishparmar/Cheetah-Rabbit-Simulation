package part1.ui;

import processing.core.*;

public class Button {
    private float x; // x position of top-left corner
    private float y; // y position of top-left corner
    private int width; // width of the button
    private int height; // height of the button
    private int ltCol; // line and text colour
    private int bgCol; // main fill colour
    private String label; // text label
    protected PApplet p; // processing

    /* constructor */
    public Button(float x, float y, int width, int height, int ltCol, int bgCol, String label, PApplet p) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.ltCol = ltCol;
        this.bgCol = bgCol;
        this.label = label;
        this.p = p;
    }

    /* alternative constructor, which defaults to black and white button when line/text colour and bg colours not specified */
    public Button(float x, float y, int width, int height, String label, PApplet p) {
        this(x, y, width, height, p.color(0), p.color(255), label, p);
    }

    /* display the button to the screen */
    public void display() {
        p.pushStyle();

        p.rectMode(PConstants.CENTER);
        p.textAlign(PConstants.CENTER, PConstants.CENTER);
        p.textSize(height/2);

        // check if mouse is within bounds of button, invert colours if so
        if (p.mouseX > x && p.mouseX < x + width && p.mouseY > y && p.mouseY < y + height) {
            p.fill(ltCol);
            p.rect(x + width/2, y + height/2, width, height);
            p.fill(bgCol);
            p.text(label, x + width/2, y + height/2);
        }
        else {
            p.fill(bgCol);
            p.rect(x + width/2, y + height/2, width, height);
            p.fill(ltCol);
            p.text(label, x + width/2, y + height/2);
        }

        p.popStyle();
    }

    /* returns true if the button has been clicked on */
    public boolean isClicked() {
        return p.mouseX > x && p.mouseX < x + width && p.mouseY > y && p.mouseY < y + height;
    }

}
