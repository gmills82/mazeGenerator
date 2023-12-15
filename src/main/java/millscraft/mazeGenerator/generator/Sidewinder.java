package millscraft.mazeGenerator.generator;

import millscraft.mazeGenerator.Cell;
import millscraft.mazeGenerator.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The Sidewinder algorithm creates mazes with long horizontal tunnels.
 * Its bias creates an empty row at the top and a mostly empty column on
 * the right side of the maze.
 *
 * @author Grant Mills
 * @since 3/9/18
 */
public class Sidewinder implements GeneratorAlgorithm {

	@Override
	public Grid prepareMaze(Grid maze) {
		if (null == maze) {
			throw new IllegalArgumentException("Maze grid cannot be null");
		}

		//Iterate over cells
		for (int x = 0; x < maze.getGrid().size(); x++) {
			List<Cell> row = maze.getGrid().get(x);

			List<Cell> grouping = new ArrayList<>();
			for (Cell currentCell : row) {
				Cell eastN = currentCell.getEast();
				Cell northN = currentCell.getNorth();
				// If we're at the top of the maze we always select east
				if (null == northN) {
					currentCell.link(eastN);
				} else {
					//Add current cell to grouping
					grouping.add(currentCell);
					// Flip a coin
					int randomInt = ThreadLocalRandom.current().nextInt(0, 2);
					// Heads we go east
					if (randomInt == 0) {
						// If we've hit the eastern edge of the maze then pick north
						// This prevents inaccessible cells
						if (null == eastN) {
							currentCell.link(northN);
							grouping.clear();
						} else {
							currentCell.link(eastN);
						}
					} else if (randomInt == 1) {
						// Tails we go north on a random cell in the grouping
						int randomInt2 = ThreadLocalRandom.current().nextInt(0, grouping.size());
						Cell randomCellFromGrouping = grouping.get(randomInt2);
						//Link the randomly selected cell's northern neighbor
						randomCellFromGrouping.link(randomCellFromGrouping.getNorth());
						// Clear group
						grouping.clear();
					}
				}
			}
		}

		return maze;
	}
}
