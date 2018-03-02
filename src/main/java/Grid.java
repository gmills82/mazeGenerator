import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Grant Mills
 * @since 3/2/18
 */
public class Grid {
	private List<List<Cell>> grid;
	private Integer rowSize;
	private Integer columnSize;

	public Grid(Integer rowSize, Integer columnSize) {
		this.rowSize = rowSize;
		this.columnSize = columnSize;

		//Create a 2d grid of cells
		createGrid(rowSize, columnSize);
		configureCellNeighbors();
	}

	//Creates the grid of cells
	private void createGrid(Integer rowSize, Integer columnSize) {
		this.grid = new ArrayList<List<Cell>>();
		for(int x = 0; x < rowSize; x++) {
			ArrayList<Cell> row = new ArrayList<Cell>();
			grid.set(x, row);
			for(int y = 0; y < columnSize; y++) {
				row.set(y, new Cell(x,y));
			}
		}
	}

	//Sets references to a cell's neighbors on each cell
	private void configureCellNeighbors() {
		for(List<Cell> row : this.grid) {
			for(Cell cell : row) {

				//North
				Optional<Cell> northOption = doesCellExistInGrid(cell.getRow() - 1, cell.getColumn());
				northOption.ifPresent(cell::setNorth);
				//South
				Optional<Cell> southOption = doesCellExistInGrid(cell.getRow() + 1, cell.getColumn());
				southOption.ifPresent(cell::setNorth);
				//East
				Optional<Cell> eastOption = doesCellExistInGrid(cell.getRow(), cell.getColumn() + 1);
				eastOption.ifPresent(cell::setNorth);
				//West
				Optional<Cell> westOption = doesCellExistInGrid(cell.getRow(), cell.getColumn() - 1);
				westOption.ifPresent(cell::setNorth);
			}
		}
	}

	/**
	 * Returns an optional cell for the given coords. Optional is empty
	 * if the cell doesn't exist in this grid
	 * @param row
	 * @param column
	 * @return
	 */
	private Optional<Cell> doesCellExistInGrid(Integer row, Integer column){
		Cell checkedCell=null;

		if(row >= 0 && row < grid.size()) {
			if(grid.get(row) != null && grid.get(row) != Collections.EMPTY_LIST) {
				if(column >= 0 && column < grid.get(row).size()) {
					checkedCell = grid.get(row).get(column);
				}
			}
		}
		Optional<Cell> checkedCellOptional = Optional.ofNullable(checkedCell);

		return checkedCellOptional;
	}

}
