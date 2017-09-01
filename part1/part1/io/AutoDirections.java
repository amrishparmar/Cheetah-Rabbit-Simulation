package part1.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import part1.collections.Queue;
import part1.constants.*;

public class AutoDirections {
    private Queue<Direction> rabbitQueue; // queue of directions for the rabbit to follow
    private Queue<Direction> cheetahQueue; // queue of directions for the cheetah to follow

    /* load the directions from file, filepath is a string corresponding to the file path and animal is the enum of the animal  */
    public boolean loadDirections(String filePath, Entity animal) {
        try {
            // create a Scanner object and try to load the file
            Scanner scan = new Scanner(new File(filePath));
            // create an empty temporary queue
            Queue<Direction> tempQueue = new Queue<>();

            // while the file still has instructions within continue enqueuing them into the temporary queue
            while (scan.hasNext()) {
                String current = scan.next();

                switch (current) {
                    case "u":
                        tempQueue.enqueue(Direction.UP);
                        break;
                    case "d":
                        tempQueue.enqueue(Direction.DOWN);
                        break;
                    case "l":
                        tempQueue.enqueue(Direction.LEFT);
                        break;
                    case "r":
                        tempQueue.enqueue(Direction.RIGHT);
                        break;
                }
            }

            // store the loaded directions into the correct animal queue
            switch (animal) {
                case CHEETAH:
                    cheetahQueue = tempQueue;
                    break;
                case RABBIT:
                    rabbitQueue = tempQueue;
                    break;
                default:
                    return false;
            }

            return true;
        }
        catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
            return false;
        }
    }

    /* getters */

    public Queue<Direction> getRabbitQueue() {
        return rabbitQueue;
    }

    public Queue<Direction> getCheetahQueue() {
        return cheetahQueue;
    }
}
