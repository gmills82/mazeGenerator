package millscraft.mazeGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Grant Mills
 * @since 3/2/18
 */
public class Grid {
	private List<List<Cell>> grid;
	private Integer rowSize;
	private Integer columnSize;
	private Integer size;

	/**
	 * @param rowSize    - height of the maze
	 * @param columnSize - width of the maze
	 */
	public Grid(Integer rowSize, Integer columnSize) {
		this.rowSize = rowSize;
		this.columnSize = columnSize;
		this.size = rowSize * columnSize;

		//Create a 2d grid of cells
		createGrid(rowSize, columnSize);
		configureCellNeighbors();
	}

	//Creates the grid of cells
	private void createGrid(Integer rowSize, Integer columnSize) {
		this.grid = new ArrayList<>();
		for (int x = 0; x < rowSize; x++) {
			ArrayList<Cell> row = new ArrayList<>();

			this.grid.add(row);
			for (int y = 0; y < columnSize; y++) {
				row.add(new Cell(x, y));
			}
		}
	}

	//Sets references to a cell's neighbors on each cell
	private void configureCellNeighbors() {
		for (List<Cell> row : this.grid) {
			for (Cell cell : row) {

				//North
				Optional<Cell> northOption = doesCellExistInGrid(cell.getRow() - 1, cell.getColumn());
				northOption.ifPresent(cell::setNorth);

				//South
				Optional<Cell> southOption = doesCellExistInGrid(cell.getRow() + 1, cell.getColumn());
				southOption.ifPresent(cell::setSouth);
				//East
				Optional<Cell> eastOption = doesCellExistInGrid(cell.getRow(), cell.getColumn() + 1);
				eastOption.ifPresent(cell::setEast);
				//West
				Optional<Cell> westOption = doesCellExistInGrid(cell.getRow(), cell.getColumn() - 1);
				westOption.ifPresent(cell::setWest);
			}
		}
	}

	/**
	 * Returns an optional cell for the given coords. Optional is empty
	 * if the cell doesn't exist in this grid
	 *
	 * @param row    - a row of the maze
	 * @param column - a column of the maze
	 * @return - {@link Optional<Cell>} optional cell if present in grid
	 */
	public Optional<Cell> doesCellExistInGrid(Integer row, Integer column) {
		Cell checkedCell = null;

		if (row >= 0 && row < grid.size()) {
			if (grid.get(row) != null && grid.get(row) != Collections.EMPTY_LIST) {
				if (column >= 0 && column < grid.get(row).size()) {
					checkedCell = grid.get(row).get(column);
				}
			}
		}

		return Optional.ofNullable(checkedCell);
	}


	/**
	 * Returns a random cell from the maze
	 *
	 * @return - {@link Cell} random cell
	 */
	public Cell getRandomCell() {
		int rowValue = ThreadLocalRandom.current().nextInt(0, this.rowSize);
		int columnValue = ThreadLocalRandom.current().nextInt(0, this.columnSize);
		return this.grid.get(rowValue).get(columnValue);
	}

	public Cell getRandomUnlinkedCell() {
		Cell possibleCell = this.getRandomCell();
		while (!possibleCell.getLinkedCells().isEmpty()) {
			possibleCell = this.getRandomCell();
		}
		return possibleCell;
	}

	public Cell getRandomUnvisitedCell() {
		Cell possibleCell = this.getRandomCell();
		while (possibleCell.hasBeenVisited()) {
			possibleCell = this.getRandomCell();
		}
		return possibleCell;
	}

	public List<List<Cell>> getGrid() {
		return grid;
	}

	public Integer getRowSize() {
		return rowSize;
	}

	public Integer getColumnSize() {
		return columnSize;
	}

	public Integer getSize() {
		return size;
	}
}
