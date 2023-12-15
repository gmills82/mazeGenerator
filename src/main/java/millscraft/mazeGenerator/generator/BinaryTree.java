package millscraft.mazeGenerator.generator;

import millscraft.mazeGenerator.Cell;
import millscraft.mazeGenerator.Grid;

import java.util.concurrent.ThreadLocalRandom;

/**
 * A very simple algorithm for generating a maze.
 * Its bias creates a maze with an empty top row and right column.
 *
 * @author Grant Mills
 * @since 3/9/18
 */
public class BinaryTree implements GeneratorAlgorithm {

	@Override
	public Grid prepareMaze(Grid maze) {
		if (null == maze) {
			throw new IllegalArgumentException("Maze grid cannot be null");
		}

		//Iterate over cells
		for (int x = 0; x < maze.getGrid().size(); x++) {
			java.util.List<Cell> row = maze.getGrid().get(x);
			for (Cell currentCell : row) {
				//Get each North and East neighbor cell
				Cell northN = currentCell.getNorth();
				Cell eastN = currentCell.getEast();

				if (null == northN) {
					currentCell.link(eastN);
				} else if (null == eastN) {
					currentCell.link(northN);
				} else {
					//Flip a coin
					int randomNumber = ThreadLocalRandom.current().nextInt(0, 2);
					if (randomNumber == 0) {
						//Heads you link to the north cell

						currentCell.link(northN);
					} else if (randomNumber == 1) {
						//Tails you link to the east
						currentCell.link(eastN);
					}
				}
			}
		}

		return maze;
	}
}
