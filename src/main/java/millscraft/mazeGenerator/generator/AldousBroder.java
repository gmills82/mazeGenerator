package millscraft.mazeGenerator.generator;

import millscraft.mazeGenerator.Cell;
import millscraft.mazeGenerator.Grid;

/**
 * The Aldous-Broder algorithm for creating a maze takes a long time
 * to complete, due to its circuitous and random nature.
 *
 * @author Grant Mills
 * @since 3/18/18
 */
public class AldousBroder implements GeneratorAlgorithm {

	@Override
	public Grid prepareMaze(Grid maze) {
		if (null == maze) {
			throw new IllegalArgumentException("Maze grid cannot be null");
		}

		//Start in a random cell in the maze
		Cell currentCell = maze.getRandomCell();
		int unvisitedCells = maze.getSize() - 1;

		//As long as there are unvisited cells
		while (unvisitedCells > 0) {
			//Get a random neighboring cell
			Cell randomNeighbor = currentCell.getRandomNeighbor();

			//If the random neighbor cell has no existing links then link it to the current cell
			if (randomNeighbor.getLinkedCells().isEmpty()) {
				//Link them and decrement the count of unvisited cells
				randomNeighbor.link(currentCell);
				unvisitedCells -= 1;
			}

			//Either way make the random neighbor cell the current cell
			currentCell = randomNeighbor;
		}

		return maze;
	}
}
