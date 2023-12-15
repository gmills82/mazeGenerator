package millscraft.mazeGenerator.generator;

import millscraft.mazeGenerator.Cell;
import millscraft.mazeGenerator.Grid;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Grant Mills
 * @since 4/4/18
 */
public class Wilson implements GeneratorAlgorithm {

    @Override
    public Grid prepareMaze(Grid maze) {
        //Get starting cell and begin count of unvisited cells
        Cell startingCell = maze.getRandomCell();
        startingCell.setVisited(true);
        int cellsUnvisited = maze.getSize() - 1;

        while (cellsUnvisited > 0) {
            Cell currentPathCell = maze.getRandomUnvisitedCell();
            ArrayList<Cell> path = new ArrayList<>();
            path.add(currentPathCell);

            //Walk the path until we hit a visited cell
            while (!currentPathCell.hasBeenVisited()) {
                currentPathCell = currentPathCell.getRandomNeighbor();
                //If the neighbor is already in the path then
                //erase the loop
                if (path.contains(currentPathCell)) {
                    int index = path.indexOf(currentPathCell);
                    int pathSize = path.size();
                    if (pathSize > index + 1) {
                        path.subList(index + 1, pathSize).clear();
                    }
                } else {
                    path.add(currentPathCell);
                }
            }

            //Link the path cells
            Iterator<Cell> it = path.iterator();
            if (it.hasNext()) {
                Cell previous = it.next();
                previous.setVisited(true);
                cellsUnvisited -= 1;

                while (it.hasNext()) {
                    Cell current = it.next();

                    //Link, set as visited, and decrement unvisited count
                    previous.link(current);

                    //Set as visited and decrement unvisited count unless its the last cell
                    //in the path, which has already been visited
                    if (it.hasNext()) {
                        current.setVisited(true);
                        cellsUnvisited -= 1;
                    }

                    previous = current;
                }
            }
        }

        return maze;
    }
}
