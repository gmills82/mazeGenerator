package millscraft.mazeGenerator.generator;

import millscraft.mazeGenerator.Cell;
import millscraft.mazeGenerator.Grid;

/**
 * @author Grant Mills
 * @since 3/18/18
 */
public class AldousBroder implements GeneratorAlgorithm {

	@Override
	public Grid prepareMaze(Grid maze) {
		if(null == maze) {
			throw new IllegalArgumentException("Maze grid cannot be null");
		}

		//Start in a random cell in the maze
		Cell currentCell = maze.getRandomCell();
		Integer unvisitedCells = maze.getSize() - 1;

		//As long as there are unvisited cells
		while(unvisitedCells > 0) {
			//Get a random neighboring cell
			Cell randomNeighbor = currentCell.getRandomNeighbor();

			//If the current cell and the randomNeighbor are not already linked
			if(!currentCell.isLinked(randomNeighbor)) {
				//Link them and decrement the count of unvisited cells
				currentCell.link(randomNeighbor);
				unvisitedCells -= 1;
			}
			//Either way make the random neighbor cell the current cell
			currentCell = randomNeighbor;
		}

		return maze;
	}
}
