package part2.core;

import part2.animals.*;
import part2.constants.*;
import part2.graph.*;
import part2.ui.*;
import processing.core.*;

import java.util.ArrayList;
import java.util.Random;

public class Sketch extends PApplet {
    private Menu mainMenu; // the main menu object
    private Grid grid; // represents the grid during program operation
    private Cheetah cheetah; // represents the cheetah
    private Rabbit rabbit; // represents the rabbit
    private RabbitHole hole; // represents the rabbit hole
    private States state; // instance of enum which represents the current state of the program
    private Message userMessage; // message that displays to the user at various times

    private boolean secondMove = false; // needed for fast cheetah mode to store whether the current move should a cheetah's second move

    @Override
    public void settings() {
        size(600, 650);
    }

    /* (re)initialise all variables to their starting values */
    private void reinitialise() {
        Random random = new Random();

        int gridDim = 12; // number of blocks in each grid dimension - arbitrary choice, can be changed easily

        // set up the grid and ui elements
        mainMenu = new Menu(this);
        grid = new Grid(gridDim, this);
        userMessage = new Message(this);

        // create the animals at a random location
        rabbit = new Rabbit(random.nextInt(gridDim), 1 + random.nextInt(gridDim), this);
        cheetah = new Cheetah(random.nextInt(gridDim), 1 + random.nextInt(gridDim), this);

        // prevent the rabbit and cheetah from starting on the same square if necessary
        while (rabbit.getPosition().equalTo(cheetah.getPosition())) {
            rabbit.setPosition(new GridBlock(random.nextInt(gridDim), 1 + random.nextInt(gridDim), this));
        }

        // generate the hole at it's default location
        hole = new RabbitHole(color(168, 132, 119), this);

        // set the initial state to the main menu
        state = States.MAIN_MENU;
    }

    @Override
    public void setup() {
        reinitialise();
    }

    @Override
    public void draw() {
        background(255);

        switch (state) {
            case MAIN_MENU:
                mainMenu.display();
                break;

            case INSTRUCTIONS:
                userMessage.display();
                break;

            case PAINTING:
            case PAINTING_FAST_CHEETAH:
                displayGameObjects();
                handlePainting();
                break;

            case IN_GAME:
                displayGameObjects();
                handlePainting();
                handlePathing();

                // activate the rabbit hole after a certain total number of moves made by the animals
                if (Animal.getTotalMoves() >= 20 && !hole.isActive()) {
                    placeRabbitHole();
                }
                break;

            case SIMULATION:
                displayGameObjects();
                handlePainting();
                handlePathing();
                // make the moves every half second (approx)
                if (frameCount % 30 == 0) {
                    // move the rabbit if there is a valid path
                    if (rabbit.getPath() != null) {
                        if (rabbit.getPath().getHead() != null) {
                            rabbit.move(rabbit.getPath().getHead().getItem());
                        }
                    }

                    // check whether the rabbit moved onto a location which ends the round
                    if (checkEndGameScenarios()){
                        break;
                    }

                    // move the cheetah if there is a valid path
                    if (cheetah.getPath() != null) {
                        if (cheetah.getPath().getHead() != null) {
                            cheetah.move(cheetah.getPath().getHead().getItem());
                        }
                    }
                    // check the scenario again after the cheetah has moved
                    checkEndGameScenarios();
                }
                break;

            case SIMULATION_FAST_CHEETAH:
                displayGameObjects();
                handlePainting();
                handlePathing();
                // make the moves every half second (approx)
                if (frameCount % 30 == 0) {
                    // if we're on the cheetah's first move
                    if (!secondMove) {
                        // move the rabbit if there's a valid path
                        if (rabbit.getPath() != null) {
                            if (rabbit.getPath().getHead() != null) {
                                rabbit.move(rabbit.getPath().getHead().getItem());
                            }
                        }

                        // check whether the rabbit moved onto a location which ends the round
                        if (checkEndGameScenarios()){
                            break;
                        }

                        // move the cheetah if there's a valid path and store whether the game has already ended
                        boolean endGame = false;
                        if (cheetah.getPath() != null) {
                            if (cheetah.getPath().getHead() != null) {
                                cheetah.move(cheetah.getPath().getHead().getItem());
                                endGame = checkEndGameScenarios();
                            }

                            // get the distance from the cheetah to the rabbit
                            int dist = Search.manhattanDistance(cheetah.getPosition(), rabbit.getPosition());

                            // if the distance is between 1 and 4 inclusive and it can "see" the rabbit, then the cheetah "sprints" to try and catch up
                            if (dist < 5 && (cheetah.getPosition().getX() == rabbit.getPosition().getX() || cheetah.getPosition().getY() == rabbit.getPosition().getY())) {
                                // if there's a second move available and the game hasn't ended
                                if (cheetah.getPath().getAtIndex(1) != null && !endGame) {
                                    secondMove = true;
                                }
                            }
                        }
                    }
                    // if we're on the second move then only want to update the cheetah
                    else {
                        if (cheetah.getPath().getHead() != null) {
                            cheetah.move(cheetah.getPath().getHead().getItem());
                        }
                        secondMove = false;
                    }

                    // check the end game scenarios again
                    checkEndGameScenarios();
                }
                break;

            case PRE_END:
                displayGameObjects();
                break;

            case END_OF_ROUND:
                userMessage.display();
        }
    }

    @Override
    public void mouseClicked() {
        switch (state) {
            case MAIN_MENU:
                if (mainMenu.getGameOption().isClicked()) {
                    state = States.IN_GAME;
                }
                else if (mainMenu.getSimulationOption().isClicked()) {
                    state = States.PAINTING;
                    placeRabbitHole();
                }
                else if (mainMenu.getFastCheetahOption().isClicked()) {
                    state = States.PAINTING_FAST_CHEETAH;
                    placeRabbitHole();
                }
                else if (mainMenu.getInstructionsOption().isClicked()) {
                    state = States.INSTRUCTIONS;
                    userMessage.setMessage("1P controls: w - UP, s - DOWN, a - LEFT, d - RIGHT\n" +
                            "Left-click on a square to mark as unwalkable, right-click to unmark it.\n" +
                            "Press SPACE to start in simulation mode.");
                }
                break;

            case INSTRUCTIONS:
                if (userMessage.getToMenu().isClicked()) {
                    state = States.MAIN_MENU;
                }
                break;

            case END_OF_ROUND:
                if (userMessage.getToMenu().isClicked()) {
                    state = States.MAIN_MENU;
                    reinitialise();
                }
                break;
        }
    }

    @Override
    public void keyPressed() {
        switch (state) {
            case PAINTING:
                if (key == ' ') {
                    state = States.SIMULATION;
                }
                break;

            case PAINTING_FAST_CHEETAH:
                if (key == ' ') {
                    state = States.SIMULATION_FAST_CHEETAH;
                }
                break;

            case IN_GAME:
                // if a valid move was made
                if (handleMovement(key)) {
                    handlePathing();

                    // check whether the rabbit moved onto a location which ends the round
                    if (checkEndGameScenarios()) {
                        break;
                    }

                    // if the cheetah has a valid valid move it
                    if (cheetah.getPath() != null) {
                        if (cheetah.getPath().getHead() != null) {
                            cheetah.move(cheetah.getPath().getHead().getItem());
                        }
                    }

                    // check the game end scenarios again
                    checkEndGameScenarios();
                }
                break;

            case PRE_END:
                state = States.END_OF_ROUND;
                break;
        }
    }

    /* handles movement for the game, boolean return needed for one player game to prevent cheetah moving on random keypress */
    public boolean handleMovement(char key) {
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

    /* display all game mode objects to the screen */
    public void displayGameObjects() {
        grid.display();
        Statistics.displayMoves(rabbit, cheetah, this);

        // only display the rabbit hole if it has been activated
        if (hole.isActive()) {
            hole.display(grid);
        }

        cheetah.display(grid);
        rabbit.display(grid);
    }

    /* handles painting of obstacles */
    public void handlePainting() {
        if (mousePressed) {
            if (mouseButton == LEFT) {
                grid.paintObstacles(mouseX, mouseY, rabbit, cheetah, hole, LEFT);
            }
            else if (mouseButton == RIGHT) {
                grid.paintObstacles(mouseX, mouseY, rabbit, cheetah, hole, RIGHT);
            }
        }
    }

    /* sets the paths of the two animals */
    public void handlePathing() {
        // set the cheetah's path to the rabbit
        cheetah.setPath(Search.aStarSearch(grid, cheetah, rabbit, this));

        // if the hole has been activated set the rabbit's path to the hole
        if (hole.isActive()) {
            rabbit.setPath(Search.aStarSearch(grid, rabbit, hole, this));
        }
    }

    /* places the rabbit hole in a rabbit position not currently occupied by one of the animals */
    public void placeRabbitHole() {
        Random rnd = new Random();
        ArrayList<GridBlock> walkableBlocks = new ArrayList<>();

        // iterate over all blocks of the grid
        for (int x = 0; x < grid.getNumBlocks(); ++x) {
            for (int y = 0; y < grid.getNumBlocks(); ++y) {
                // getAtIndex the current grid block
                GridBlock current = grid.getBlocks()[x][y];
                // check that it's walkable and that it isn't occupied by either animal before adding to arraylist
                if (current.isWalkable() && !current.equalTo(cheetah.getPosition()) && !current.equalTo(rabbit.getPosition())) {
                    walkableBlocks.add(current);
                }
            }
        }

        // grab a random walkable block from the list
        GridBlock newHolePosition = walkableBlocks.get(rnd.nextInt(walkableBlocks.size()));

        // set the position of the hole and mark as active
        hole.setPosition(newHolePosition);
        hole.setActive(true);
    }

    /* check that a game end scenario happened, i.e. rabbit escaped to the hole or cheetah caught the rabbit */
    public boolean checkEndGameScenarios() {
        if (rabbit.escaped(hole) && hole.isActive()) {
            // increment the appropriate score
            switch (state) {
                case IN_GAME:
                    Scores.incrementOneVsCpu();
                    break;
                case SIMULATION:
                case SIMULATION_FAST_CHEETAH:
                    Scores.incrementCpuRabbitVsCpuCheetah();
                    break;
            }

            state = States.PRE_END;
            userMessage.setMessage("The rabbit successfully escaped." + "\nCheetah moves: " + cheetah.getMoves() + "\nRabbit moves: " + rabbit.getMoves());
            return true;
        }
        else if (rabbit.hasBeenCaught(cheetah)) {
            // increment the appropriate score
            switch (state) {
                case IN_GAME:
                    Scores.incrementCpuVsOne();
                    break;
                case SIMULATION:
                case SIMULATION_FAST_CHEETAH:
                    Scores.incrementCpuCheetahVsCpuRabbit();
                    break;
            }

            state = States.PRE_END;
            userMessage.setMessage("The cheetah caught the rabbit." + "\nCheetah moves: " + cheetah.getMoves() + "\nRabbit moves: " + rabbit.getMoves());
            return true;
        }
        return false;
    }
}
