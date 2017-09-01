package part2.graph;

import part2.animals.*;
import part2.collections.LinkedList;
import processing.core.*;

import java.util.ArrayList;
import java.util.HashSet;

public class Search {

    /* empty private constructor to prevent instantiation */
    private Search() {}

    /* we use Manhattan distance as our heuristic */
    private static int heuristic(GridBlock start, GridBlock end) {
        int dx = start.getX() - end.getX();
        int dy = start.getY() - end.getY();

        return Math.abs(dx) + Math.abs(dy);
    }

    /* public alias for getting the manhattanDistance */
    public static int manhattanDistance(GridBlock start, GridBlock end) {
        return heuristic(start, end);
    }

//    /* we use Euclidean distance as our heuristic */
//    private static int heuristic(GridBlock start, GridBlock end) {
//        float dx = Math.abs(start.getX() - end.getX());
//        float dy = Math.abs(start.getY() - end.getY());
//
//        return (int)Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
//    }
//
//    /* public alias for getting the manhattanDistance */
//    public static int euclideanDistance(GridBlock start, GridBlock end) {
//        return heuristic(start, end);
//    }

    /* returns a LinkedList of GridBlocks that contain the path from the start animal position to the end  */
    public static LinkedList<GridBlock> aStarSearch(Grid grid, Animal start, Animal end, PApplet p) {
        // if the start and end positions are the same, there's no point in searching
        if (start.getPosition().equalTo(end.getPosition())) {
            return null;
        }

        // create a NodeBlock from start and end since they are GridBlocks
        NodeBlock startNode = new NodeBlock(start.getPosition(), p);
        NodeBlock endNode = new NodeBlock(end.getPosition(), p);

        // track the number of iterations so that the algorithm doesn't search infinitely if it's trying to find a blocked off route
        int iterations = 0;

        // list of nodes to visit
        ArrayList<NodeBlock> openSet = new ArrayList<>();
        // list of nodes we have already visited - use HashSet since we don't want repeated elements
        HashSet<NodeBlock> closedSet = new HashSet<>();

        // add our start node as the first node to visit
        openSet.add(startNode);

        // whilst we have no more nodes to visit
        while (!openSet.isEmpty() && iterations < 8 * Math.pow(grid.getNumBlocks(), 2)) {
            // grab the first node in the open set
            NodeBlock current = openSet.get(0);
            // look for the most promising node, i.e. the one with the lowest fcost or lowest hcost if fcost is the same
            for (int i = 1; i < openSet.size(); ++i) {
                if (openSet.get(i).fCost() < current.fCost() || openSet.get(i).fCost() == current.fCost() && openSet.get(i).getHCost() < current.getHCost()) {
                    current = openSet.get(i);
                }
            }

            // remove the current node from the open set and add it to closed
            openSet.remove(current);
            closedSet.add(current);

            // if the current node is the same as the destination, then we can return the path that we found
            if (current.equalTo(endNode)) {
                endNode.setParent(current.getParent());
                return retrace(startNode, endNode);
            }

            // the new cost of moving to a neighbour will be the current one plus 1 since all directions have same cost
            int newMoveCost = current.getGCost() + 1;

            // examine all walkable neighbours of the current node
            for (NodeBlock neighbour : grid.getWalkableNeighbours(current)) {
                // if we have not already examined this neighbour
                if (!closedSet.contains(neighbour)) {
                    // if the new cost of moving is less then the neighbour g cost or the open set doesn't contain the neighbour
                    if (newMoveCost < neighbour.getGCost() || !openSet.contains(neighbour)) {
                        // update the neighbour g cost with the new cost since this a better route
                        neighbour.setGCost(newMoveCost);
                        // calculate the h cost distance  using the heuristic
                        neighbour.setHCost(heuristic(neighbour, endNode));
                        // add the current node as the neighbour's parent so we can retrace our steps
                        neighbour.setParent(current);
                        // add our neighbour to the open set if it's not in there already
                        if (!openSet.contains(neighbour)) {
                            openSet.add(neighbour);
                        }
                    }
                }
            }
            iterations++;
        }
        return null;
    }

    /* retraces the path from the end node back to the start node and returns a linked list with path in correct order */
    private static LinkedList<GridBlock> retrace(NodeBlock start, NodeBlock end) {
        ArrayList<NodeBlock> reversedPath = new ArrayList<>();
        NodeBlock current = end;

        // track back the path taken
        while (current != start) {
            reversedPath.add(current);
            current = current.getParent();
        }

        // need to put it back in order, adding to linked list does this for us
        LinkedList<GridBlock> path = new LinkedList<>();
        for (NodeBlock block : reversedPath) {
            path.add(block);
        }

        return path;
    }
}
