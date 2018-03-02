import java.util.Set;

/**
 * @author Grant Mills
 * @since 3/2/18
 */
public class Cell {
	private Integer row;
	private Integer column;
	private Set<Cell> linkedCells;
	private Cell north;
	private Cell south;
	private Cell east;
	private Cell west;

	public Cell(Integer row, Integer column) {
		this.row = row;
		this.column = column;
	}

	public Integer getRow() {
		return row;
	}

	public Integer getColumn() {
		return column;
	}

	public Cell getNorth() {
		return north;
	}

	public void setNorth(Cell north) {
		this.north = north;
	}

	public Cell getSouth() {
		return south;
	}

	public void setSouth(Cell south) {
		this.south = south;
	}

	public Cell getEast() {
		return east;
	}

	public void setEast(Cell east) {
		this.east = east;
	}

	public Cell getWest() {
		return west;
	}

	public void setWest(Cell west) {
		this.west = west;
	}
}
