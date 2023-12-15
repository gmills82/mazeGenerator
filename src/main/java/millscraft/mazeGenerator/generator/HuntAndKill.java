package millscraft.mazeGenerator.generator;

import millscraft.mazeGenerator.Cell;
import millscraft.mazeGenerator.Grid;

import java.util.Optional;

/**
 * @author Grant Mills
 * @since 4/10/18
 */
public class HuntAndKill implements GeneratorAlgorithm {

    @Override
    public Grid prepareMaze(Grid maze) {
        Cell current = maze.getRandomCell();
        current.setVisited(true);

        while (current != null) {
            Optional<Cell> possibleNeighbor = current.getRandomUnvisitedNeighbor();

            //Link to a random unvisited neighbor
            if (possibleNeighbor.isPresent()) {
                current.link(possibleNeighbor.get());
                current = possibleNeighbor.get();
                current.setVisited(true);
            } else {
                current = null;

                //Hunt for a new starting point where an unvisited cell has a
                //visited neighbor and link them.
                for (int x = 0; x < maze.getGrid().size(); x++) {
                    java.util.List<Cell> row = maze.getGrid().get(x);
                    for (Cell potentialNewStartingCell : row) {
                        Optional<Cell> potentialNewStartingCellVisitedNeighbor = potentialNewStartingCell.getRandomVisitedNeighbor();
                        if (!potentialNewStartingCell.hasBeenVisited() && potentialNewStartingCellVisitedNeighbor.isPresent()) {
                            current = potentialNewStartingCell;
                            current.setVisited(true);
                            current.link(potentialNewStartingCellVisitedNeighbor.get());
                        }
                    }
                }
            }
        }

        return maze;
    }
}
