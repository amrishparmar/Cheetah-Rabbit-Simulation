package part1.core;

import java.util.Random;

import processing.core.*;

import part1.animals.*;
import part1.constants.*;
import part1.graph.Grid;
import part1.graph.GridBlock;
//import part1.graph.*;
import part1.io.AutoDirections;
import part1.ui.*;

public class Sketch extends PApplet {
    private Menu mainMenu; // the main menu object
    private Grid grid; // represents the grid during program operation
    private Cheetah cheetah; // represents the cheetah
    private Rabbit rabbit; // represents the rabbit
    private RabbitHole hole; // represents the rabbit hole
    private States state; // instance of enum which represents the current state of the program
    private Message userMessage; // message that displays to the user at various times
    private AutoDirections autoDirections; // will be initialised to contain the direction queues for auto mode

    /* processing's settings() method */
    @Override
    public void settings() {
        size(600, 650);
    }

    /* (re)initialise all variables to their starting values */
    private void reinitialise() {
        int gridDim = 10; // number of blocks in each grid dimension - arbitrary choice, can be changed easily

        mainMenu = new Menu(this);
        grid = new Grid(gridDim, this);
        cheetah = new Cheetah(gridDim-1, gridDim, this);
        rabbit = new Rabbit(0, 1, this);
        hole = new RabbitHole(color(168, 132, 119), this);
        state = States.MAIN_MENU;
        userMessage = new Message(this);
    }

    /* processing's setup() method */
    @Override
    public void setup() {
        reinitialise();
    }

    /* processing's draw() method - called every frame */
    @Override
    public void draw() {
        background(255);

        // check the current state and perform corresponding actions
        switch (state) {
            case MAIN_MENU:
                mainMenu.display();
                break;

            case INSTRUCTIONS:
                userMessage.display();
                break;

            case ONEPLAYER_GAME:
            case TWOPLAYER_GAME:
            case FILE_AUTO:
                // activate the rabbit hole after a certain total number of moves made by the animals
                if (Animal.getTotalMoves() >= 20 && !hole.isActive()) {
                    placeRabbitHole();
                }

                // if we are in the file auto state want to automatically carry out loaded autoDirections
                if (state == States.FILE_AUTO) {
                    if (frameCount % 45 == 0) {
                        boolean endGame = false;

                        if (!autoDirections.getRabbitQueue().isEmpty()) {
                            rabbit.move(autoDirections.getRabbitQueue().dequeue(), grid);
                            endGame = checkEndGameScenarios();
                        }
                        // don't bother moving the cheetah if we have already reached an endgame scenario
                        if (!autoDirections.getCheetahQueue().isEmpty() && !endGame) {
                            cheetah.move(autoDirections.getCheetahQueue().dequeue(), grid);
                            checkEndGameScenarios();
                        }
                    }
                }

                drawEverything();
                break;

            case PRE_END:
                drawEverything();
                break;

            case FILE_ERROR:
            case END_OF_ROUND:
                userMessage.display();
                break;
        }
    }

    /* processing's keyPressed() method - triggers when any key is pressed */
    @Override
    public void keyPressed() {
        // check the current state and only respond to key presses when in an appropriate one
        switch (state) {
            case MAIN_MENU:
                break;

            case ONEPLAYER_GAME:
                if (handleMovement(key)) {
                    cheetah.chase(rabbit, grid);
                    checkEndGameScenarios();
                }
                break;

            case TWOPLAYER_GAME:
                handleMovement(key);
                checkEndGameScenarios();
                break;

            case PRE_END:
                state = States.END_OF_ROUND;
                break;
        }
    }

    /* processing's mouseClicked() method - triggers when the mouse is clicked */
    @Override
    public void mouseClicked() {
        // check the current state and only respond to mouse clicks when in an appropriate one
        switch (state) {
            case MAIN_MENU:
                if (mainMenu.getOnePlayerOption().isClicked()) {
                    state = States.ONEPLAYER_GAME;
                }
                else if (mainMenu.getTwoPlayerOption().isClicked()) {
                    state = States.TWOPLAYER_GAME;
                }
                else if (mainMenu.getFileAutoOption().isClicked()) {
                    state = States.FILE_AUTO;

                    // load the directions from file, storing the return value to check whether it was successful
                    autoDirections = new AutoDirections();
                    boolean rLoadSuccess = autoDirections.loadDirections("rabbit_directions.txt", Entity.RABBIT);
                    boolean cLoadSuccess = autoDirections.loadDirections("cheetah_directions.txt", Entity.CHEETAH);

                    // if there was an error trigger the error state and set an appropriate error message
                    if (!rLoadSuccess || !cLoadSuccess) {
                        state = States.FILE_ERROR;
                        if (!rLoadSuccess && !cLoadSuccess) {
                            userMessage.setMessage("Error loading \"rabbit_directions.txt\" and \"cheetah_directions.txt\"\nPlease ensure they are in the program directory.");
                        }
                        else if (!rLoadSuccess) {
                            userMessage.setMessage("Error loading \"rabbit_directions.txt\"\nPlease ensure it is in the program directory.");
                        }
                        else {
                            userMessage.setMessage("Error loading \"cheetah_directions.txt\"\nPlease ensure it is in the program directory.");
                        }
                    }
                }
                else if (mainMenu.getInstructionsOption().isClicked()) {
                    state = States.INSTRUCTIONS;
                    userMessage.setMessage("1P controls: w - UP, s - DOWN, a - LEFT, d - RIGHT\n" +
                            "2P controls: i - UP, k - DOWN, j - LEFT, l - RIGHT\n" +
                            "rabbit_directions.txt and cheetah_directions.txt must be in same directory as program for auto mode");
                }
                break;

            case INSTRUCTIONS:
                state = States.MAIN_MENU;
                break;

            case ONEPLAYER_GAME:
            case TWOPLAYER_GAME:
                break;

            case FILE_ERROR:
            case END_OF_ROUND:
                if (userMessage.getToMenu().isClicked()) {
                    reinitialise();
                }
                break;
        }
    }

    /* display all of the objects to the screen */
    public void drawEverything() {
        grid.draw();
        Statistics.display(rabbit, cheetah, this);

        // only display the rabbit hole if it has been activated
        if (hole.isActive()) {
            hole.draw(grid);
        }

        cheetah.display(grid);
        rabbit.display(grid);
    }

    /* places the rabbit hole in a rabbit position not currently occupied by one of the animals */
    public void placeRabbitHole() {
        Random rnd = new Random();
        GridBlock newHolePosition;

        // keep placing the position until we get an unoccupied space
        do {
            newHolePosition = new GridBlock(rnd.nextInt(grid.getNumBlocks()), rnd.nextInt(grid.getNumBlocks()) + 1, this);
        } while (newHolePosition.equalTo(cheetah.getPosition()) || newHolePosition.equalTo(rabbit.getPosition()));

        // set the position of the hole and mark as active
        hole.setPosition(newHolePosition);
        hole.setActive(true);
    }

    /* handles movement for the game, boolean return needed for one player game to prevent cheetah moving on random keypress */
    public boolean handleMovement(char key) {
        // only bother checking for cheetah keypresses in the 2 player state
        if (state == States.TWOPLAYER_GAME) {
            // check for cheetah movement keypresses
            switch (key) {
                case 'i': case 'I':
                    return cheetah.move(Direction.UP, grid);
                case 'j': case 'J':
                    return cheetah.move(Direction.LEFT, grid);
                case 'k': case 'K':
                    return cheetah.move(Direction.DOWN, grid);
                case 'l': case 'L':
                    return cheetah.move(Direction.RIGHT, grid);
            }
        }

        // check for rabbit movement keypresses
        switch (key) {
            case 'w': case 'W':
                return rabbit.move(Direction.UP, grid);
            case 'a': case 'A':
                return rabbit.move(Direction.LEFT, grid);
            case 's': case 'S':
                return rabbit.move(Direction.DOWN, grid);
            case 'd': case 'D':
                return rabbit.move(Direction.RIGHT, grid);
        }

        return false;
    }

    /* check that a game end scenario happened, i.e. rabbit escaped to the hole or cheetah caught the rabbit */
    public boolean checkEndGameScenarios() {
        if (rabbit.escaped(hole) && hole.isActive()) {
            // increment the appropriate score
            switch (state) {
                case ONEPLAYER_GAME:
                    Scores.incrementOneVsCpu();
                    break;
                case TWOPLAYER_GAME:
                    Scores.incrementOneVsTwo();
                    break;
            }
            state = States.PRE_END;
            userMessage.setMessage("The rabbit successfully escaped." + "\nCheetah moves: " + cheetah.getMoves() + "\nRabbit moves: " + rabbit.getMoves());
            return true;
        }
        else if (rabbit.hasBeenCaught(cheetah)) {
            // increment the appropriate score
            switch (state) {
                case ONEPLAYER_GAME:
                    Scores.incrementCpuVsOne();
                    break;
                case TWOPLAYER_GAME:
                    Scores.incrementTwoVsOne();
                    break;
            }
            state = States.PRE_END;
            userMessage.setMessage("The cheetah caught the rabbit." + "\nCheetah moves: " + cheetah.getMoves() + "\nRabbit moves: " + rabbit.getMoves());
            return true;
        }
        return false;
    }
}
